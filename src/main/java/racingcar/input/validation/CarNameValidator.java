package racingcar.input.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import racingcar.constants.InputConstants;

public class CarNameValidator implements Validation<String> {

    @Override
    public boolean validation(String inputValue) {
        throw new UnsupportedOperationException("지원되지 않는 검증 메소드 입니다.");
    }

    @Override
    public void validationThrowsIfFailed(String inputValue) {
        validationThrowsIfNullOrBlanks(inputValue);

        String[] separatedNames = inputValue.split(",");
        List<String> names = List.of(separatedNames);

        validationThrowsIfNotMinimumAmountOfCars(names);
        validationThrowsIfIncorrectNameLength(names);
        validationThrowsIfNameDuplicated(names);
    }

    private void validationThrowsIfNullOrBlanks(String inputValue) {
        if (inputValue == null || inputValue.isBlank() || inputValue.contains(" ")) {
            throw new IllegalArgumentException("자동차 이름 입력에는 공백이 들어갈 수 없습니다.");
        }
    }

    private void validationThrowsIfNotMinimumAmountOfCars(List<String> names) {
        if (names.size() < InputConstants.MINIMUM_AMOUNT_CARS) {
            throw new IllegalArgumentException("원활한 자동차 경주를 위해 2대 이상 입력 해주세요.");
        }
    }

    private void validationThrowsIfIncorrectNameLength(List<String> names) {
        for (String name : names) {
            int nameLength = name.length();
            if (nameLength <= InputConstants.MAXIMUM_CAR_NAME_LENGTH && nameLength > 0) {
                continue;
            }
            throw new IllegalArgumentException("자동차 이름은 1자 이상 " +
                    InputConstants.MAXIMUM_CAR_NAME_LENGTH + "자 이하 이어야 합니다");
        }
    }

    private void validationThrowsIfNameDuplicated(List<String> names) {
        Set<String> duplicated = new HashSet<>(names);
        if (names.size() != duplicated.size()) {
            throw new IllegalArgumentException("자동차 이름은 중복될 수 없습니다.");
        }
    }
}
