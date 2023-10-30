package racingcar.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import camp.nextstep.edu.missionutils.Randoms;
import racingcar.framework.dependency.Inject;
import racingcar.constants.AppConstants;
import racingcar.domain.Car;
import racingcar.domain.CarRepository;

public class CarService {

    private final CarRepository carRepository;

    @Inject
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void registerCars(String carNamesText) {
        String[] names = carNamesText.split(",");
        for (String name : names) {
            Car car = Car.create(name);
            carRepository.addCar(car);
        }
    }

    public void race(int tryCount) {
        List<Car> cars = carRepository.getCars();

        System.out.println("\n실행 결과");
        for (int i = 0; i < tryCount; i++) {
            for (Car car : cars) {
                int random = Randoms.pickNumberInRange(0, 9);
                if (random >= AppConstants.CAR_FORWARD_CONDITION_VALUE) {
                    car.increaseDistance();
                }
                System.out.println(car);
            }
            System.out.println();
        }
    }

    public void printResults() {
        List<Car> cars = carRepository.getCars();
        List<Car> sortableCars = new ArrayList<>(cars);
        sortableCars.sort(Comparator.comparing(Car::getDistance, Comparator.reverseOrder()));
        int highestDistance = sortableCars.get(0).getDistance();
        String winners = sortableCars.stream()
                .filter(car -> car.getDistance() == highestDistance)
                .map(Car::getName)
                .collect(Collectors.joining(", "));

        System.out.print("최종 우승자 : " + winners);
    }
}
