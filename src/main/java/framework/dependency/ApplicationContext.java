package framework.dependency;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import framework.dependency.holder.ComponentHolder;

public final class ApplicationContext {

    private final Set<ComponentHolder> injectedInstances;
    private final DependencyInjector dependencyInjector;

    ApplicationContext(List<Object> configurations, List<Class<?>> injectTargets) {
        this.dependencyInjector = new ApplicationContextDependencyInjector();
        this.injectedInstances = new HashSet<>(this.dependencyInjector.configures(configurations));
        this.dependencyInjector.injects(injectTargets, injectedInstances);
    }

    public <T> T getInstance(Class<T> targetClass) {
        return null;
    }

    public void injects(List<Class<?>> injectTargets) {
        dependencyInjector.injects(injectTargets, injectedInstances);
    }
}
