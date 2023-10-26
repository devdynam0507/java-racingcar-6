package racingcar.configuration;

import framework.dependency.Component;
import framework.dependency.Configuration;
import racingcar.domain.Cars;

@Configuration
public class GameConfiguration {

    @Component(name = "cars")
    public Cars cars() {
        return new Cars();
    }
}
