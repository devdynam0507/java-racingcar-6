package racingcar.configuration;

import racingcar.framework.dependency.Component;
import racingcar.framework.dependency.Configuration;

import racingcar.domain.CarRepository;

@Configuration
public class GameConfiguration {

    @Component(name = "carRepository")
    public CarRepository cars() {
        return new CarRepository();
    }
}
