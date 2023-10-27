package racingcar.handlers;

import framework.dependency.Inject;
import framework.event.EventListener;
import framework.event.EventPublisher;
import racingcar.domain.CarRepository;
import racingcar.event.RaceEndEvent;
import racingcar.event.RaceBeginEvent;
import racingcar.event.RaceEvent;
import racingcar.service.CarService;

public class CarEventHandler {

    private final CarService carService;

    @Inject
    public CarEventHandler(CarService carService) {
        this.carService = carService;
    }

    @EventListener
    public void onCarRaceStartEvent(RaceBeginEvent event, EventPublisher eventPublisher) {
        String inputText = event.inputText();
        Integer tryCount = event.tryCount();

        carService.registerCars(inputText);
        carService.race(tryCount);

        eventPublisher.dispatch(new RaceEndEvent());
    }

    @EventListener
    public void onCarRaceFinishEvent(RaceEndEvent event, EventPublisher eventPublisher) {
        carService.printResults();
    }
}
