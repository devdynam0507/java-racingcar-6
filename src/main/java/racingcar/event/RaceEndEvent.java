package racingcar.event;

import java.util.List;

import racingcar.domain.Car;

public record RaceEndEvent(List<Car> cars, int raceCount) {}
