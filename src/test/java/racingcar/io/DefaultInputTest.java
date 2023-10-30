package racingcar.io;

import org.junit.jupiter.api.Test;

import racingcar.common.Streams;

import static org.junit.jupiter.api.Assertions.*;

class DefaultInputTest {

    @Test
    void 정상적인_숫자_입력() throws Exception {
        try(Input input = new DefaultInput()) {
            String inputText = "1";
            System.setIn(Streams.stringToByteInputStream(inputText));

            Integer integer = input.nextInt();

            assertEquals(integer, 1);
        }
    }

    @Test
    void 비정상적인_숫자_입력() throws Exception {
        try(Input input = new DefaultInput()) {
            String inputText = "not number";
            System.setIn(Streams.stringToByteInputStream(inputText));

            assertThrows(IllegalArgumentException.class, input::nextInt);
        }
    }
}