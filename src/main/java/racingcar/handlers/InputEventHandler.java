package racingcar.handlers;

import framework.dependency.Inject;
import framework.event.EventListener;
import framework.event.EventPublisher;
import racingcar.event.RaceBeginEvent;
import racingcar.event.InputEvent;
import racingcar.io.Input;
import racingcar.io.validation.CarNameValidator;
import racingcar.io.validation.Validation;

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
        String line = input.nextLine();
        carNameValidator.validationThrowsIfFailed(line);

        System.out.println("시도할 회수는 몇회인가요?");
        Integer count = input.nextInt();

        RaceBeginEvent raceEvent = new RaceBeginEvent(line, count);
        eventPublisher.dispatch(raceEvent);
    }
}
