package racingcar.framework.dependency.objects;

import racingcar.framework.dependency.Inject;

public class TestValueChild extends TestValueObject {

    @Inject
    public TestValueChild(String value) {
        super(value);
    }
}
