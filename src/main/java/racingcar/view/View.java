package racingcar.view;

import java.util.List;
import java.util.stream.Collectors;

import racingcar.domain.Car;

public class View {

    public void printWinner(List<Car> sortedCars) {
        String winnerNames = sortedCars.stream()
                .map(Car::getName)
                .collect(Collectors.joining(", "));
        System.out.println("최종 우승자 : " + winnerNames);
    }

    public void printCarStatesWithProceedStates(List<Car> cars) {
        System.out.println("\n실행 결과");
        printCarStates(cars);
    }

    public void printEachWithTitle(String title, List<?> list) {
        System.out.println(title);
        list.forEach(System.out::println);
    }

    public void printCarStates(List<Car> cars) {
        cars.forEach(System.out::println);
    }
}
