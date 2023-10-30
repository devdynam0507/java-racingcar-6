package racingcar.configuration;

import racingcar.handlers.CarRaceBeginEventHandler;
import racingcar.handlers.CarRaceEndEventHandler;
import racingcar.handlers.CarRaceProceedingEventHandler;
import racingcar.handlers.EventListenerInjectHandler;
import racingcar.handlers.InputEventHandler;

public class AppConfiguration {

    public static Object[] configurations() {
        return new Object[] {
            new EventConfiguration(),
            new GameConfiguration(),
            new InputConfiguration(),
            new ValidationConfiguration()
        };
    }

    public static EventListenerInjectHandler eventDrivenConfiguration() {
        return new EventListenerInjectHandler(
            CarRaceBeginEventHandler.class,
            CarRaceProceedingEventHandler.class,
            CarRaceEndEventHandler.class,
            InputEventHandler.class
        );
    }
}
