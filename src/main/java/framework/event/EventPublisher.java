package framework.event;

public interface EventPublisher extends EventRegistry {

    void dispatch(Object eventObject);
}
