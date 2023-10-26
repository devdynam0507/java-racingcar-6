package racingcar.domain;

import java.util.stream.IntStream;

public class Car {

    private final String name;
    private int distance;

    private Car(String name, int distance) {
        this.name = name;
        this.distance = distance;
    }

    public void increaseDistance() {
        distance += 1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(" : ");
        IntStream.range(0, distance).forEach(v -> stringBuilder.append("-"));
        return stringBuilder.toString();
    }

    public static Car create(String name) {
        return new Car(name, 0);
    }
}
