package racingcar.handlers;

import racingcar.domain.Car;
import racingcar.domain.CarRepository;

import racingcar.event.RaceBeginEvent;
import racingcar.event.RaceProceedEvent;

import racingcar.framework.dependency.Inject;
import racingcar.framework.event.EventListener;
import racingcar.framework.event.EventPublisher;

public class CarRaceBeginEventHandler {

    private final CarRepository carRepository;

    @Inject
    public CarRaceBeginEventHandler(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @EventListener
    public void onRaceBegin(RaceBeginEvent event, EventPublisher eventPublisher) {
        String carNameText = event.inputText();
        Integer raceCount = event.raceCount();

        String[] names = carNameText.split(",");
        for (String name : names) {
            Car car = Car.create(name);
            carRepository.addCar(car);
        }

        eventPublisher.dispatch(new RaceProceedEvent(carRepository, raceCount));
    }
}
