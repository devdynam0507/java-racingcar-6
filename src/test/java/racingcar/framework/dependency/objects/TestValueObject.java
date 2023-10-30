package racingcar.framework.dependency.objects;

public class TestValueObject {

    private final String value;

    public TestValueObject(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
