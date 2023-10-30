package racingcar.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarRepositoryTest {

    CarRepository carRepository;

    @BeforeEach
    void before() {
        carRepository = new CarRepository();

        carRepository.addCar(Car.create("car1"));
    }

    @Test
    void immutable_한_리스트로_잘_반환되는지() {
        List<Car> cars = carRepository.getCars();

        assertThrows(UnsupportedOperationException.class, () -> cars.add(Car.create("newCar")));
    }
}