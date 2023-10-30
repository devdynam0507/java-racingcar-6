package racingcar.handlers.service;

import java.util.ArrayList;
import java.util.List;

import camp.nextstep.edu.missionutils.Randoms;
import racingcar.constants.GameConstants;
import racingcar.domain.Car;
import racingcar.domain.CarRepository;
import racingcar.framework.dependency.Inject;

public class DefaultCarCommandService implements CarCommandService {

    private final CarRepository carRepository;

    @Inject
    public DefaultCarCommandService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void update(Car car, int randomForwardNumber) {
        if (randomForwardNumber < GameConstants.CAR_FORWARD_CONDITION_VALUE) {
            return;
        }
        car.increaseDistance();
    }

    @Override
    public void updateAll() {
        List<Car> cars = carRepository.getCars();
        for (Car car : cars) {
            int random = Randoms.pickNumberInRange(0, 9);
            update(car, random);
        }
    }

    @Override
    public Car createCar(String name) {
        return Car.create(name);
    }

    @Override
    public List<Car> createCars(List<String> names) {
        List<Car> cars = new ArrayList<>();

        for (String carName : names) {
            Car car = createCar(carName);
            cars.add(car);
        }

        return cars;
    }

    @Override
    public void save(Car car) {
        carRepository.addCar(car);
    }

    @Override
    public void saveAll(List<Car> cars) {
        carRepository.addAll(cars);
    }
}
