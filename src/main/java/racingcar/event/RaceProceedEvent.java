package racingcar.event;

import racingcar.domain.CarRepository;

public record RaceProceedEvent(CarRepository carRepository, int raceCount) {}
