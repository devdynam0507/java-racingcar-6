package racingcar.framework.event.exceptions;

public class EventListenerRegistrationException extends EventListenerException {

    public EventListenerRegistrationException(String listenerName, String message) {
        super(listenerName, message);
    }
}
