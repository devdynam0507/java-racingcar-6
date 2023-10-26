package racingcar.io.validation;

import java.util.HashSet;
import java.util.Set;

public class CarNameValidator implements Validation<String> {

    @Override
    public boolean validation(String inputValue) {
        if (inputValue == null || inputValue.isBlank() || inputValue.contains(" ")) {
            return false;
        }
        String[] names = inputValue.split(",");
        if (names.length < 2) {
            return false;
        }
        Set<String> duplicateCheckSet = new HashSet<>();
        for (String name : names) {
            if (name.length() > 5 || name.length() < 1) {
                return false;
            }
            duplicateCheckSet.add(name);
        }
        return duplicateCheckSet.size() == names.length;
    }

    @Override
    public void validationThrowsIfFailed(String inputValue) {
        if (!validation(inputValue)) {
            throw new IllegalArgumentException("쉼표 기준 문자열이 2개 이상, 공백 미포함, 이름별 글자 제한은 1자 이상 5자 이하 입니다.");
        }
    }
}
