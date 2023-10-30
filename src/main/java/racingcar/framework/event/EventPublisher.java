package racingcar.framework.event;

public interface EventPublisher extends EventRegistry {

    void dispatch(Object eventObject);

    void dispatch(Class<?> eventClass);

    void dispatch(Class<?> eventClass, Object eventObject);
}
