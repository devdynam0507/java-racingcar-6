package racingcar;

import racingcar.framework.event.EventDrivenApplication;
import racingcar.framework.event.EventPublisher;

import racingcar.event.InputEvent;

import static racingcar.configuration.AppConfiguration.*;

public class Application {
    public static void main(String[] args) {
        EventPublisher eventPublisher = new EventDrivenApplication(eventDrivenConfiguration())
                .configurations(configurations())
                .build();

        eventPublisher.dispatch(InputEvent.class);
    }
}
