package racingcar.handlers.games;

import java.util.List;
import java.util.stream.Collectors;

import racingcar.domain.Car;

import racingcar.event.RaceEndEvent;

import racingcar.framework.dependency.Inject;
import racingcar.framework.event.EventListener;
import racingcar.framework.event.EventPublisher;

import racingcar.handlers.service.CarQueryService;
import racingcar.handlers.service.Order;

import racingcar.view.View;

public class CarRaceEndEventHandler {

    private final CarQueryService carQueryService;
    private final View view;

    @Inject
    public CarRaceEndEventHandler(CarQueryService carQueryService, View view) {
        this.carQueryService = carQueryService;
        this.view = view;
    }

    @EventListener
    public void onRaceEnd(RaceEndEvent event, EventPublisher eventPublisher) {
        List<Car> winners = carQueryService.findAllByHighestCarsOrderBy(Order.DESC);
        String winnerNames = winners.stream()
                .map(Car::getName)
                .collect(Collectors.joining(", "));

        view.printWinner(winnerNames);
    }
}
