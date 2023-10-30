package racingcar.framework.dependency;

import java.util.List;
import java.util.Set;

import racingcar.framework.dependency.holder.ComponentHolder;

public interface DependencyInjector {

    List<ComponentHolder> configures(List<Object> configurationObjects);

    void injects(List<Class<?>> injectTargets, Set<ComponentHolder> createdInstances);
}
