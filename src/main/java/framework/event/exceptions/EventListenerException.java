package framework.event.exceptions;

public class EventListenerException extends RuntimeException {

    private final String listenerName;

    public EventListenerException(String listenerName, String message) {
        super(message);
        this.listenerName = listenerName;
    }

    public String getListenerName() {
        return listenerName;
    }
}
