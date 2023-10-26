package racingcar.event;

import racingcar.io.Input;

public class InputEvent {

    private final Input input;

    private InputEvent(Input input) {
        this.input = input;
    }

    public Input getInput() {
        return input;
    }

    public static InputEvent create(Input input) {
        return new InputEvent(input);
    }
}
