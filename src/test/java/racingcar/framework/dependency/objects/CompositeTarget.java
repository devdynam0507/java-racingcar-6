package racingcar.framework.dependency.objects;

import racingcar.framework.dependency.Inject;

public class CompositeTarget {

    private TestValueObject testValueObject;

    @Inject
    public CompositeTarget(TestValueObject testValueObject) {
        this.testValueObject = testValueObject;
    }

    public TestValueObject getTestValueObject() {
        return testValueObject;
    }
}
