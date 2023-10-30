package racingcar.configuration;

import racingcar.framework.dependency.Component;
import racingcar.framework.dependency.Configuration;

import racingcar.domain.CarRepository;
import racingcar.handlers.service.CarCommandService;
import racingcar.handlers.service.CarQueryService;
import racingcar.handlers.service.DefaultCarCommandService;
import racingcar.handlers.service.DefaultCarQueryService;
import racingcar.view.View;

@Configuration
public class GameConfiguration {

    @Component
    public View view() {
        return new View();
    }

    @Component(name = "carRepository")
    public CarRepository cars() {
        return new CarRepository();
    }

    @Component
    public CarCommandService carCommandService(CarRepository carRepository) {
        return new DefaultCarCommandService(carRepository);
    }

    @Component
    public CarQueryService carQueryService(CarRepository carRepository) {
        return new DefaultCarQueryService(carRepository);
    }
}
