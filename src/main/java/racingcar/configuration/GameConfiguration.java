package racingcar.configuration;

import framework.dependency.Component;
import framework.dependency.Configuration;

import racingcar.domain.CarRepository;
import racingcar.service.CarService;

@Configuration
public class GameConfiguration {

    @Component(name = "carRepository")
    public CarRepository cars() {
        return new CarRepository();
    }

    @Component(name = "carService")
    public CarService carService(CarRepository carRepository) {
        return new CarService(carRepository);
    }
}
