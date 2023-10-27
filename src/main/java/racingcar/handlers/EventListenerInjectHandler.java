package racingcar.handlers;

import java.util.List;

import framework.dependency.ApplicationContext;
import framework.dependency.PostInjectListener;
import framework.event.EventPublisher;

public class EventListenerInjectHandler implements PostInjectListener {

    private final List<Class<?>> handlers;

    public EventListenerInjectHandler(Class<?>... handlers) {
        this(List.of(handlers));
    }

    public EventListenerInjectHandler(List<Class<?>> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void onInitialized(ApplicationContext applicationContext) {
        EventPublisher eventPublisher = applicationContext.getInstance(EventPublisher.class);
        if (eventPublisher == null) {
            return;
        }
        applicationContext.injects(handlers);
        for (Class<?> handler : handlers) {
            Object eventListener = applicationContext.getInstance(handler);
            eventPublisher.registerEvent(eventListener);
        }
    }
}
