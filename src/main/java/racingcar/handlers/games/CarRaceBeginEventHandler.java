package racingcar.handlers.games;

import java.util.List;

import racingcar.domain.Car;

import racingcar.event.RaceBeginEvent;
import racingcar.event.RaceProceedEvent;

import racingcar.framework.dependency.Inject;
import racingcar.framework.event.EventListener;
import racingcar.framework.event.EventPublisher;

import racingcar.handlers.service.CarCommandService;

public class CarRaceBeginEventHandler {

    private final CarCommandService carCommandService;

    @Inject
    public CarRaceBeginEventHandler(CarCommandService carCommandService) {
        this.carCommandService = carCommandService;
    }

    @EventListener
    public void onRaceBegin(RaceBeginEvent event, EventPublisher eventPublisher) {
        String carNameText = event.inputText();
        Integer raceCount = event.raceCount();

        List<String> names = List.of(carNameText.split(","));
        List<Car> cars = carCommandService.createCars(names);
        carCommandService.saveAll(cars);

        eventPublisher.dispatch(new RaceProceedEvent(raceCount));
    }
}
