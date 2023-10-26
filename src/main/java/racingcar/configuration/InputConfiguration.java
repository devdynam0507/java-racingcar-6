package racingcar.configuration;

import framework.dependency.Component;
import framework.dependency.Configuration;
import racingcar.io.DefaultInput;
import racingcar.io.Input;

@Configuration
public class InputConfiguration {

    @Component(name = "input")
    public Input input() {
        return new DefaultInput();
    }
}
