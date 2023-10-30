package racingcar;

import racingcar.framework.dependency.ApplicationContext;
import racingcar.framework.dependency.ApplicationContextBuilder;
import racingcar.framework.event.EventPublisher;
import racingcar.configuration.EventConfiguration;
import racingcar.configuration.GameConfiguration;
import racingcar.configuration.InputConfiguration;
import racingcar.configuration.ValidationConfiguration;
import racingcar.event.InputEvent;
import racingcar.handlers.CarEventHandler;
import racingcar.handlers.EventListenerInjectHandler;
import racingcar.handlers.InputEventHandler;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                ApplicationContextBuilder.builder()
                        .configuration(new EventConfiguration())
                        .configuration(new GameConfiguration())
                        .configuration(new InputConfiguration())
                        .configuration(new ValidationConfiguration())
                        .addListener(new EventListenerInjectHandler(
                                CarEventHandler.class,
                                InputEventHandler.class
                        ))
                        .build();

        EventPublisher eventPublisher = applicationContext.getInstance(EventPublisher.class);
        eventPublisher.dispatch(new InputEvent());
    }
}
