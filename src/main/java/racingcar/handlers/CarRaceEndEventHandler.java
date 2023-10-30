package racingcar.handlers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import racingcar.domain.Car;
import racingcar.event.RaceEndEvent;
import racingcar.framework.event.EventListener;
import racingcar.framework.event.EventPublisher;

public class CarRaceEndEventHandler {

    @EventListener
    public void onRaceEnd(RaceEndEvent event, EventPublisher eventPublisher) {
        List<Car> cars = event.cars();
        List<Car> sortedCars = sortCarsByDistanceOrderDescending(cars);
        int highestDistance = sortedCars.get(0).getDistance();
        String winners = sortedCars.stream()
                .filter(car -> car.getDistance() == highestDistance)
                .map(Car::getName)
                .collect(Collectors.joining(", "));

        System.out.print("최종 우승자 : " + winners);
    }

    List<Car> sortCarsByDistanceOrderDescending(List<Car> cars) {
        List<Car> sortableCars = new ArrayList<>(cars);
        sortableCars.sort(Comparator.comparing(Car::getDistance, Comparator.reverseOrder()));
        return sortableCars;
    }
}
