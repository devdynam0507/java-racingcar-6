package racingcar.input.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarNameValidatorTest {

    private final CarNameValidator validator = new CarNameValidator();

    @Test
    void 공백_문자열_테스트() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validationThrowsIfFailed("");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validationThrowsIfFailed(" ");
        });
    }

    @Test
    void 널_체크_테스트() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validationThrowsIfFailed(null);
        });
    }

    @Test
    void 레이싱_자동차가_한대_밖에_없는_경우() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validationThrowsIfFailed("자동차");
        });
    }

    @Test
    void 중복_자동차_입력() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validationThrowsIfFailed("자동차1,자동차1");
        });
    }

    @Test
    void 정상적인_입력() {
        assertAll(
                () -> validator.validationThrowsIfFailed("자동차1,자동차2,"),
                () -> validator.validationThrowsIfFailed("자동차1,자동차2,자동차3"),
                () -> validator.validationThrowsIfFailed("자동차1,자동차2,자동차3,자동차4"),
                () -> validator.validationThrowsIfFailed("자동차1,자동차2,자동차3,자동차4,자동차5")
        );
    }
}