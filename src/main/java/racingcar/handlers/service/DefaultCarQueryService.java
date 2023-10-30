package racingcar.handlers.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import racingcar.domain.Car;
import racingcar.domain.CarRepository;

import racingcar.framework.dependency.Inject;

public class DefaultCarQueryService implements CarQueryService {

    private final CarRepository carRepository;

    @Inject
    public DefaultCarQueryService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> findAll() {
        return carRepository.getCars();
    }

    @Override
    public List<Car> findAllOrderBy(Order order) {
        List<Car> cars = findAll();
        List<Car> sortableCars = new ArrayList<>(cars);
        if (order == Order.DESC) {
            sortableCars.sort(Comparator.comparing(Car::getDistance, Comparator.reverseOrder()));
        }
        else {
            sortableCars.sort(Comparator.comparing(Car::getDistance));
        }
        return sortableCars;
    }

    @Override
    public List<Car> findAllByHighestCarsOrderBy(Order order) {
        List<Car> cars = findAllOrderBy(order);
        int highestDistance = cars.get(0).getDistance();
        return cars.stream()
                .filter(car -> car.getDistance() == highestDistance)
                .toList();
    }
}
