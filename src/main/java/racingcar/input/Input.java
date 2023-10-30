package racingcar.input;

public interface Input extends AutoCloseable {

    String nextLine();

    Integer nextInt();
}
