package racingcar.service;

import framework.dependency.Inject;
import racingcar.domain.Car;
import racingcar.domain.CarRepository;

public class CarService {

    private CarRepository carRepository;

    @Inject
    public CarService(CarRepository carRepository) {
        System.out.println("instantiated: " + carRepository);
        this.carRepository = carRepository;
    }

    public void registerCars(String carNamesText) {
        String[] names = carNamesText.split(",");
        for (String name : names) {
            Car car = Car.create(name);
            carRepository.addCar(car);
        }
    }
}
