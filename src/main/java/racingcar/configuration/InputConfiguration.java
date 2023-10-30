package racingcar.configuration;

import racingcar.framework.dependency.Component;
import racingcar.framework.dependency.Configuration;
import racingcar.input.DefaultInput;
import racingcar.input.Input;

@Configuration
public class InputConfiguration {

    @Component(name = "input")
    public Input input() {
        return new DefaultInput();
    }
}
