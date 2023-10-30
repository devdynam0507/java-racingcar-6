package racingcar.framework.dependency.objects;

import racingcar.framework.dependency.Inject;

public class AmbiguousQualifierTarget {

    @Inject
    public AmbiguousQualifierTarget(TestObject object1, TestObject object2) {}
}
