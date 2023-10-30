package racingcar.handlers;

import java.util.List;
import java.util.stream.Collectors;

import racingcar.domain.Car;
import racingcar.event.RaceEndEvent;
import racingcar.framework.dependency.Inject;
import racingcar.framework.event.EventListener;
import racingcar.framework.event.EventPublisher;
import racingcar.handlers.service.CarQueryService;
import racingcar.handlers.service.Order;

public class CarRaceEndEventHandler {

    private final CarQueryService carQueryService;

    @Inject
    public CarRaceEndEventHandler(CarQueryService carQueryService) {
        this.carQueryService = carQueryService;
    }

    @EventListener
    public void onRaceEnd(RaceEndEvent event, EventPublisher eventPublisher) {
        List<Car> cars = carQueryService.findAllOrderBy(Order.DESC);
        int highestDistance = cars.get(0).getDistance();
        String winners = cars.stream()
                .filter(car -> car.getDistance() == highestDistance)
                .map(Car::getName)
                .collect(Collectors.joining(", "));

        System.out.print("최종 우승자 : " + winners);
    }
}
