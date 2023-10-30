package racingcar.configuration;

import racingcar.handlers.games.CarRaceBeginEventHandler;
import racingcar.handlers.games.CarRaceEndEventHandler;
import racingcar.handlers.games.CarRaceProceedingEventHandler;
import racingcar.handlers.games.InputEventHandler;

import racingcar.handlers.infrastructure.EventListenerInjectHandler;

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
