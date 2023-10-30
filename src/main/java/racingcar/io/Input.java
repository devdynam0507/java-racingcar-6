package racingcar.io;

public interface Input extends AutoCloseable {

    String nextLine();

    Integer nextInt();
}
