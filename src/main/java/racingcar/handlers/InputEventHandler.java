package racingcar.handlers;

import racingcar.framework.dependency.Inject;
import racingcar.framework.event.EventListener;
import racingcar.framework.event.EventPublisher;
import racingcar.event.RaceBeginEvent;
import racingcar.event.InputEvent;
import racingcar.input.Input;
import racingcar.input.validation.CarNameValidator;
import racingcar.input.validation.Validation;

public class InputEventHandler {

    private final Input input;
    private final Validation<String> carNameValidator;

    @Inject
    public InputEventHandler(Input input, CarNameValidator carNameValidator) {
        this.input = input;
        this.carNameValidator = carNameValidator;
    }

    @EventListener
    public void onInput(InputEvent event, EventPublisher eventPublisher) {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String carNameText = input.nextLine();
        carNameValidator.validationThrowsIfFailed(carNameText);

        System.out.println("시도할 회수는 몇회인가요?");
        Integer raceCount = input.nextInt();

        eventPublisher.dispatch(new RaceBeginEvent(carNameText, raceCount));
    }
}
