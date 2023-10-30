package racingcar.handlers.games;

import java.util.List;

import racingcar.domain.Car;

import racingcar.event.RaceEndEvent;
import racingcar.event.RaceProceedEvent;

import racingcar.framework.dependency.Inject;
import racingcar.framework.event.EventListener;
import racingcar.framework.event.EventPublisher;

import racingcar.handlers.service.CarCommandService;
import racingcar.handlers.service.CarQueryService;

import racingcar.view.View;

public class CarRaceProceedingEventHandler {

    private final CarCommandService carCommandService;
    private final CarQueryService carQueryService;
    private final View view;

    @Inject
    public CarRaceProceedingEventHandler(
            CarCommandService carCommandService, CarQueryService carQueryService, View view) {
        this.carCommandService = carCommandService;
        this.carQueryService = carQueryService;
        this.view = view;
    }

    @EventListener
    public void onRaceProceed(RaceProceedEvent event, EventPublisher eventPublisher) {
        int raceCount = event.raceCount();
        List<Car> cars = carQueryService.findAll();

        view.printCarProceedTitle();
        for (int i = 0; i < raceCount; i++) {
            carCommandService.updateAll();
            view.printCarStates(cars);
        }

        eventPublisher.dispatch(new RaceEndEvent(raceCount));
    }
}
