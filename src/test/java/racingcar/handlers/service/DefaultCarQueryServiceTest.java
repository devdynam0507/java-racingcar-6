package racingcar.handlers.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import racingcar.domain.Car;
import racingcar.domain.CarRepository;

class DefaultCarQueryServiceTest {

    CarQueryService carQueryService;

    @BeforeEach
    void before() {
        CarRepository carRepository = new CarRepository();
        carQueryService = new DefaultCarQueryService(carRepository);

        Car car1 = Car.create("car1");
        Car car2 = Car.create("car2");

        carRepository.addCar(car1);
        carRepository.addCar(car2);
    }

    @Test
    void 자동차가_제대로_조회_되는지() {
        List<Car> cars = carQueryService.findAll();

        assertEquals(cars.size(), 2);
        assertEquals(cars.get(0).getName(), "car1");
        assertEquals(cars.get(1).getName(), "car2");
    }

    @Test
    void 자동차_오름차순_정렬() {
        carQueryService.findAll().get(0).increaseDistance();

        List<Car> cars = carQueryService.findAllOrderBy(Order.ASC);

        assertEquals(cars.get(0).getName(), "car2");
    }

    @Test
    void 자동차_내림차순_정렬() {
        carQueryService.findAll().get(0).increaseDistance();

        List<Car> cars = carQueryService.findAllOrderBy(Order.DESC);

        assertEquals(cars.get(0).getName(), "car1");
    }

    @Test
    void 차순_별_제일_낮거나_높은_자동차를_조회() {
        carQueryService.findAll().get(0).increaseDistance();

        List<Car> carsDesc = carQueryService.findAllByHighestCarsOrderBy(Order.DESC);
        List<Car> carsAsc = carQueryService.findAllByHighestCarsOrderBy(Order.ASC);

        assertEquals(carsDesc.get(0).getName(), "car1");
        assertEquals(carsAsc.get(0).getName(), "car2");
    }
}