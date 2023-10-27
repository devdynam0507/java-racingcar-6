package racingcar.handlers;

import framework.dependency.Inject;
import framework.event.EventListener;
import framework.event.EventPublisher;
import racingcar.domain.CarRepository;
import racingcar.event.RaceEndEvent;
import racingcar.event.RaceBeginEvent;

public class CarEventHandler {

    private final CarRepository carRepository;

    @Inject
    public CarEventHandler(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @EventListener
    public void onCarRaceStartEvent(RaceBeginEvent event, EventPublisher eventPublisher) {

    }

    @EventListener
    public void onCarRaceFinishEvent(RaceEndEvent event, EventPublisher eventPublisher) {

    }
}
