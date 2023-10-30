package racingcar.handlers.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import racingcar.domain.Car;
import racingcar.domain.CarRepository;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;

import java.util.List;

class DefaultCarCommandServiceTest {

    private final static int FORWARD = 5;
    private final static int STOP = 3;

    CarCommandService carCommandService;

    @BeforeEach
    void before() {
        CarRepository carRepository = new CarRepository();
        carCommandService = new DefaultCarCommandService(carRepository);
    }

    @Test
    void 전진_신호를_받으면_잘_이동하는지() {
        Car car = Car.create("car");

        carCommandService.update(car, 5);

        assertEquals(car.getDistance(), 1);
    }

    @Test
    void 전진_신호가_아니면_이동하지않는지() {
        Car car = Car.create("car");

        carCommandService.update(car, 1);

        assertEquals(car.getDistance(), 0);
    }

    @Test
    void 자동차들이_제대로_업데이트_되는지() {
        Car car1 = Car.create("car1");
        Car car2 = Car.create("car2");

        carCommandService.saveAll(List.of(car1, car2));

        assertRandomNumberInRangeTest(
            () -> {
                carCommandService.updateAll();

                assertEquals(car1.getDistance(), 1);
                assertEquals(car2.getDistance(), 0);
            },
            FORWARD,
            STOP
        );
    }

    @Test
    void 자동차가_올바르게_생성되는지() {
        Car car = Car.create("car");

        assertEquals(car.getDistance(), 0);
        assertEquals(car.getName(), "car");
    }
}