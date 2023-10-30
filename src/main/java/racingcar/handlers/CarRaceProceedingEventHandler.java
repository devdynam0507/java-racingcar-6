package racingcar.handlers;

import java.util.List;
import java.util.PrimitiveIterator;

import camp.nextstep.edu.missionutils.Randoms;

import racingcar.constants.AppConstants;

import racingcar.domain.Car;
import racingcar.domain.CarRepository;

import racingcar.event.RaceEndEvent;
import racingcar.event.RaceProceedEvent;

import racingcar.framework.dependency.Inject;
import racingcar.framework.event.EventListener;
import racingcar.framework.event.EventPublisher;
import racingcar.handlers.service.CarCommandService;

public class CarRaceProceedingEventHandler {

    private final CarCommandService carCommandService;

    @Inject
    public CarRaceProceedingEventHandler(CarCommandService carCommandService) {
        this.carCommandService = carCommandService;
    }

    @EventListener
    public void onRaceProceed(RaceProceedEvent event, EventPublisher eventPublisher) {
        int raceCount = event.raceCount();
        carCommandService.updateAll();

        System.out.println("\n실행 결과");
        for (int i = 0; i < raceCount; i++) {
            carCommandService.updateAll();

            System.out.println();
        }

        eventPublisher.dispatch(new RaceEndEvent(raceCount));
    }
}
