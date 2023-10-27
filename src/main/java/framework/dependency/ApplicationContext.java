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
        injects(injectTargets);
    }

    public <T> T getInstance(Class<T> targetClass) {
        Object instance = injectedInstances.stream()
               .filter(holder -> targetClass.isAssignableFrom(holder.getComponentClassType()))
               .findFirst()
               .map(ComponentHolder::getInstantiatedComponent)
               .orElseThrow(() -> new IllegalArgumentException("Component not found " + targetClass.getName()));
        return (T) instance;
    }

    public void injects(List<Class<?>> injectTargets) {
        dependencyInjector.injects(injectTargets, injectedInstances);
    }
}
