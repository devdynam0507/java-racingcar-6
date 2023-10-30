package racingcar.handlers;

import java.util.List;
import java.util.PrimitiveIterator;

import camp.nextstep.edu.missionutils.Randoms;

import racingcar.constants.AppConstants;

import racingcar.domain.Car;
import racingcar.domain.CarRepository;

import racingcar.event.RaceEndEvent;
import racingcar.event.RaceProceedEvent;

import racingcar.framework.event.EventListener;
import racingcar.framework.event.EventPublisher;

public class CarRaceProceedingEventHandler {

    @EventListener
    public void onRaceProceed(RaceProceedEvent event, EventPublisher eventPublisher) {
        CarRepository carRepository = event.carRepository();
        int raceCount = event.raceCount();
        List<Car> cars = carRepository.getCars();

        System.out.println("\n실행 결과");
        for (int i = 0; i < raceCount; i++) {
            race(cars);
            printCarStates(cars);
            System.out.println();
        }

        eventPublisher.dispatch(new RaceEndEvent(cars, raceCount));
    }

    private void race(List<Car> cars) {
        for (Car car : cars) {
            int random = Randoms.pickNumberInRange(0, 9);
            if (random < AppConstants.CAR_FORWARD_CONDITION_VALUE) {
                continue;
            }
            car.increaseDistance();
        }
    }

    private void printCarStates(List<Car> cars) {
        for (Car car : cars) {
            System.out.println(car);
        }
    }
}
