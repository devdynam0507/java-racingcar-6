package racingcar.handlers;

import framework.dependency.Inject;
import framework.event.EventListener;
import framework.event.EventPublisher;
import racingcar.domain.Cars;
import racingcar.event.CarRaceFinishEvent;
import racingcar.event.CarRaceStartEvent;

public class CarEventHandler {

    private final Cars cars;

    @Inject
    public CarEventHandler(Cars cars) {
        this.cars = cars;
    }

    @EventListener
    public void onCarRaceStartEvent(CarRaceStartEvent event, EventPublisher eventPublisher) {

    }

    @EventListener
    public void onCarRaceFinishEvent(CarRaceFinishEvent event, EventPublisher eventPublisher) {

    }
}
