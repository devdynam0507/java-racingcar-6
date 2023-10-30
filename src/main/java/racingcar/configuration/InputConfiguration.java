package racingcar.configuration;

import racingcar.framework.dependency.Component;
import racingcar.framework.dependency.Configuration;
import racingcar.io.DefaultInput;
import racingcar.io.Input;

@Configuration
public class InputConfiguration {

    @Component(name = "input")
    public Input input() {
        return new DefaultInput();
    }
}
