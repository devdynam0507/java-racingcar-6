package racingcar.input.validation;

public interface Validation<Input> {

    boolean validation(Input inputValue);

    void validationThrowsIfFailed(Input inputValue);
}
