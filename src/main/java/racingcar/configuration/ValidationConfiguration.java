package racingcar.configuration;

import framework.dependency.Component;
import framework.dependency.Configuration;
import racingcar.io.validation.CarNameValidator;

@Configuration
public class ValidationConfiguration {

    @Component(name = "carNameValidator")
    public CarNameValidator carNameValidator() {
        return new CarNameValidator();
    }
}
