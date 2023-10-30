package racingcar.configuration;

import racingcar.framework.dependency.Component;
import racingcar.framework.dependency.Configuration;
import racingcar.framework.event.EventPublisher;
import racingcar.framework.event.SimpleEventDispatcher;

@Configuration
public class EventConfiguration {

    @Component(name = "eventPublisher")
    public EventPublisher eventPublisher() {
        return new SimpleEventDispatcher();
    }
}
