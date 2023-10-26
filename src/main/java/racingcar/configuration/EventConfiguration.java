package racingcar.configuration;

import framework.dependency.Component;
import framework.dependency.Configuration;
import framework.event.EventPublisher;
import framework.event.SimpleEventDispatcher;

@Configuration
public class EventConfiguration {

    @Component(name = "eventPublisher")
    public EventPublisher eventPublisher() {
        return new SimpleEventDispatcher();
    }
}
