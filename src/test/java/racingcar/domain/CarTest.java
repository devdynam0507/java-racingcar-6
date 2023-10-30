package racingcar.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CarTest {

    @Test
    void 자동차_이동거리가_올바르게_출력_되는지() {
        Car car1 = Car.create("car1");

        car1.increaseDistance();

        assertEquals(car1.toString(), "car1 : -");
    }
}