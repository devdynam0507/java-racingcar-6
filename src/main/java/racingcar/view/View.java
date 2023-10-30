package racingcar.view;

import java.util.List;

import racingcar.domain.Car;

public class View {

    public void printWinner(String winners) {
        System.out.println("최종 우승자 : " + winners);
    }

    public void printCarProceedTitle() {
        System.out.println("\n실행 결과");
    }

    public void printCarStatesWithProceedStates(List<Car> cars) {
        printCarStates(cars);
    }

    public void printEachWithTitle(String title, List<?> list) {
        System.out.println(title);
        list.forEach(System.out::println);
    }

    public void printCarStates(List<Car> cars) {
        cars.forEach(System.out::println);
        newLine();
    }

    public void printCarNameInputText() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
    }

    public void printRaceCountText() {
        System.out.println("시도할 회수는 몇회인가요?");
    }

    private void newLine() {
        System.out.println();
    }
}
