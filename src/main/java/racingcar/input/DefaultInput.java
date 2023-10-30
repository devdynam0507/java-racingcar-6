package racingcar.input;

import camp.nextstep.edu.missionutils.Console;

public class DefaultInput implements Input {

    @Override
    public String nextLine() {
        return Console.readLine();
    }

    @Override
    public Integer nextInt() {
        try {
            String line = nextLine();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력할 수 있습니다.");
        }
    }

    @Override
    public void close() throws Exception {
        Console.close();
    }
}
