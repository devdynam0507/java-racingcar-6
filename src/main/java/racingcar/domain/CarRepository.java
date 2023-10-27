package racingcar.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CarRepository {

    private final List<Car> cars;

    public CarRepository() {
        this.cars = new ArrayList<>();
    }

    public CarRepository(Car... cars) {
        this.cars = Arrays.asList(cars);
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }
}
