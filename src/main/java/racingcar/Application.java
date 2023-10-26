package racingcar;

import framework.dependency.ApplicationContext;
import framework.dependency.ApplicationContextBuilder;
import framework.event.EventPublisher;
import racingcar.configuration.EventConfiguration;
import racingcar.configuration.GameConfiguration;
import racingcar.configuration.InputConfiguration;
import racingcar.configuration.ValidationConfiguration;
import racingcar.event.InputEvent;
import racingcar.service.CarEventHandler;
import racingcar.service.EventListenerInjectHandler;
import racingcar.service.InputEventHandler;

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

        EventPublisher instance = applicationContext.getInstance(EventPublisher.class);
        if (instance != null) {
            instance.dispatch(InputEvent.create(null));
        }
    }
}
