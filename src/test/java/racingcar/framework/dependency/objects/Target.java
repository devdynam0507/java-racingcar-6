package racingcar.framework.dependency.objects;

import racingcar.framework.dependency.Inject;

public class Target {

    private final TestObject testObject;

    @Inject
    public Target(TestObject testObject) {
        this.testObject = testObject;
    }

    public TestObject getTarget() {
        return testObject;
    }
}
