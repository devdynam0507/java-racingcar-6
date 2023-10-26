package racingcar.service;

import framework.dependency.ApplicationContext;
import framework.dependency.PostInjectListener;
import framework.event.EventPublisher;

public class EventListenerInjectHandler implements PostInjectListener {

    private final Class<?>[] handlers;

    public EventListenerInjectHandler(Class<?>... handlers) {
        this.handlers = handlers;
    }

    @Override
    public void onInitialized(ApplicationContext applicationContext) {
        EventPublisher eventPublisher = applicationContext.getInstance(EventPublisher.class);
        if (eventPublisher == null) {
            return;
        }
        for (Class<?> handlerClass : handlers) {
            Object injectedHandler = applicationContext.inject(handlerClass);
            eventPublisher.registerEvent(injectedHandler);
        }
    }
}
