package racingcar.framework.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import racingcar.framework.common.reflections.MethodAnnotationHolder;
import racingcar.framework.common.reflections.ReflectionUtils;
import racingcar.framework.event.exceptions.EventListenerRegistrationException;

public class SimpleEventDispatcher implements EventPublisher {

    private final Map<Class<?>, List<EventHolder>> listeners = new HashMap<>();

    @Override
    public void dispatch(Object eventObject) {
        List<EventHolder> eventHolders = getEventListener(eventObject.getClass());
        eventHolders.forEach(eventHolder -> {
            Method method = eventHolder.getMethod();
            try {
                method.invoke(eventHolder.getEventListener(), eventObject, this);
            } catch (IllegalAccessException e1) {
            } catch (InvocationTargetException e2) {
                Throwable targetException = e2.getTargetException();
                throw (RuntimeException) targetException;
            }
        });
    }

    @Override
    public void registerEvent(Object eventListener) {
        List<EventHolder> eventListeners = createEventListeners(eventListener, EventListener.class);
        eventListeners.forEach(eventHolder -> {
            listeners.computeIfAbsent(eventHolder.getEventType(), v -> new ArrayList<>()).add(eventHolder);
        });
    }

    @Override
    public List<EventHolder> getEventListener(Class<?> eventType) {
        return listeners.getOrDefault(eventType, Collections.unmodifiableList(new ArrayList<>()));
    }

    private List<EventHolder> createEventListeners(Object eventListener, Class<? extends Annotation> clazz) {
        List<MethodAnnotationHolder> methods =
            ReflectionUtils.getMethodsWithAnnotation(eventListener, clazz);
        List<EventHolder> eventHolders = new ArrayList<>();
        for (MethodAnnotationHolder holder : methods) {
            List<Class<?>> parameters = holder.getParameterTypes();
            Method method = holder.getMethod();
            if (parameters.size() != 2) {
                throw new EventListenerRegistrationException(
                    method.getName(),
                    "The first argument of the event listener must be the Event object and "
                    + "the second argument must be the EventPublisher.");
            }
            EventHolder eventHolder = EventHolder.create(parameters.get(0), eventListener, method);
            eventHolders.add(eventHolder);
        }
        return eventHolders;
    }
}
