package racingcar.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cars {

    private final List<Car> cars;

    public Cars() {
        this.cars = new ArrayList<>();
    }

    public Cars(Car... cars) {
        this.cars = Arrays.asList(cars);
    }

    public void addCar(Car car) {
        cars.add(car);
    }
}
