package racingcar.configuration;

import racingcar.framework.dependency.Component;
import racingcar.framework.dependency.Configuration;

import racingcar.input.validation.CarNameValidator;

@Configuration
public class ValidationConfiguration {

    @Component(name = "carNameValidator")
    public CarNameValidator carNameValidator() {
        return new CarNameValidator();
    }
}
