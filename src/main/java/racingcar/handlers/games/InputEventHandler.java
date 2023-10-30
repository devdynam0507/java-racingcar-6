package racingcar.handlers.games;

import racingcar.framework.dependency.Inject;
import racingcar.framework.event.EventListener;
import racingcar.framework.event.EventPublisher;
import racingcar.event.RaceBeginEvent;
import racingcar.event.InputEvent;
import racingcar.input.Input;
import racingcar.input.validation.CarNameValidator;
import racingcar.input.validation.Validation;
import racingcar.view.View;

public class InputEventHandler {

    private final Input input;
    private final Validation<String> carNameValidator;
    private final View view;

    @Inject
    public InputEventHandler(Input input, CarNameValidator carNameValidator, View view) {
        this.input = input;
        this.carNameValidator = carNameValidator;
        this.view = view;
    }

    @EventListener
    public void onInput(InputEvent event, EventPublisher eventPublisher) {
        view.printCarNameInputText();
        String carNameText = input.nextLine();
        carNameValidator.validationThrowsIfFailed(carNameText);

        view.printRaceCountText();
        Integer raceCount = input.nextInt();

        eventPublisher.dispatch(new RaceBeginEvent(carNameText, raceCount));
    }
}
