package racingcar.framework.dependency.objects;

import racingcar.framework.dependency.Inject;
import racingcar.framework.dependency.Qualifier;

public class QualifierTarget {

    private final TestObject testObject;

    @Inject
    public QualifierTarget(@Qualifier(name = "testObject2") TestObject testObject) {
        this.testObject = testObject;
    }

    public TestObject getTarget() {
        return testObject;
    }
}
