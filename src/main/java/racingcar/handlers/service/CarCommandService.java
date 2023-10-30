package racingcar.handlers.service;

import java.util.List;

import racingcar.domain.Car;

public interface CarCommandService {

    void update(Car car, int randomForwardNumber);

    void updateAll();

    Car createCar(String name);

    List<Car> createCars(List<String> names);

    void save(Car car);

    void saveAll(List<Car> cars);
}
