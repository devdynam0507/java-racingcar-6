package racingcar.handlers;

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
        carCommandService.updateAll();

        for (int i = 0; i < raceCount; i++) {
            carCommandService.updateAll();
        }

        List<Car> cars = carQueryService.findAll();
        view.printEachWithTitle("\n실행 결과", cars);

        eventPublisher.dispatch(new RaceEndEvent(raceCount));
    }
}
