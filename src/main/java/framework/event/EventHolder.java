package framework.event;

import java.lang.reflect.Method;

public class EventHolder {

    private final Class<?> eventType;
    private final Object eventListener;
    private final Method method;

    private EventHolder(Class<?> eventType, Object eventListener, Method method) {
        this.eventType = eventType;
        this.eventListener = eventListener;
        this.method = method;
    }

    public Class<?> getEventType() {
        return eventType;
    }

    public Object getEventListener() {
        return eventListener;
    }

    public Method getMethod() {
        return method;
    }

    public static EventHolder create(Class<?> eventType, Object eventListener, Method method) {
        return new EventHolder(eventType, eventListener, method);
    }
}
