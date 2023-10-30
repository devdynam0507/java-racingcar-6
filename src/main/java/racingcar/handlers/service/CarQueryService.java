package racingcar.handlers.service;

import java.util.List;

import racingcar.domain.Car;

public interface CarQueryService {

    List<Car> findAll();

    List<Car> findAllOrderBy(Order order);

    List<Car> findAllByHighestCarsOrderBy(Order order);
}
