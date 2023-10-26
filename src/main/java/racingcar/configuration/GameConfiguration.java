package racingcar.configuration;

import framework.dependency.Component;
import racingcar.domain.Cars;

public class GameConfiguration {

    @Component(name = "cars")
    public Cars cars() {
        return new Cars();
    }
}
