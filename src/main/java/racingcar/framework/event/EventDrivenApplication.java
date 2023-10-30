package racingcar.framework.event;

import racingcar.framework.dependency.ApplicationContext;
import racingcar.framework.dependency.ApplicationContextBuilder;
import racingcar.framework.dependency.DependencyBuilder;
import racingcar.framework.dependency.PostInjectListener;

public class EventDrivenApplication implements DependencyBuilder<EventDrivenApplication, EventPublisher> {
    ApplicationContextBuilder applicationContextBuilder = ApplicationContextBuilder.builder();

    public EventDrivenApplication(PostInjectListener eventHandlerPostInjectListener) {
        applicationContextBuilder.addPostInjectionHandler(eventHandlerPostInjectListener);
    }

    @Override
    public EventDrivenApplication configuration(Object configuration) {
        configurations(configuration);
        return this;
    }

    @Override
    public EventDrivenApplication configurations(Object... configurations) {
        applicationContextBuilder.configurations(configurations);
        return this;
    }

    @Override
    public EventDrivenApplication inject(Class<?> clazz) {
        injects(clazz);
        return this;
    }

    @Override
    public EventDrivenApplication injects(Class<?>... clazz) {
        applicationContextBuilder.injects(clazz);
        return this;
    }

    @Override
    public EventDrivenApplication addPostInjectionHandler(PostInjectListener postInjectListener) {
        applicationContextBuilder.addPostInjectionHandler(postInjectListener);
        return this;
    }

    @Override
    public EventPublisher build() {
        ApplicationContext applicationContext = applicationContextBuilder.build();
        return applicationContext.getInstance(EventPublisher.class);
    }
}
