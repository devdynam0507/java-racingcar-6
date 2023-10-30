package racingcar.framework.dependency;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import racingcar.framework.dependency.holder.ComponentHolder;

public final class ApplicationContext {

    private final Set<ComponentHolder> injectedInstances;
    private final DependencyInjector dependencyInjector;

    ApplicationContext(List<Object> configurations, List<Class<?>> injectTargets) {
        this.dependencyInjector = new ApplicationContextDependencyInjector();
        this.injectedInstances = new HashSet<>();

        configure(configurations);
        injects(injectTargets);
    }

    public <T> T getInstance(Class<T> targetClass) {
        Object instance = injectedInstances.stream()
               .filter(holder -> targetClass.isAssignableFrom(holder.getComponentClassType()))
               .findFirst()
               .map(ComponentHolder::instantiatedComponent)
               .orElseThrow(() -> new IllegalArgumentException("Component not found " + targetClass.getName()));
        return (T) instance;
    }

    public <T> T getInstance(String componentName) {
        Object instance = injectedInstances.stream()
                .filter(holder -> holder.componentName().equals(componentName))
                .findFirst()
                .map(ComponentHolder::instantiatedComponent)
                .orElseThrow(() -> new IllegalArgumentException("Component not found " + componentName));
        return (T) instance;
    }

    public void injects(List<Class<?>> injectTargets) {
        dependencyInjector.injects(injectTargets, injectedInstances);
    }

    public void configure(List<Object> configures) {
        HashSet<ComponentHolder> componentHolders = new HashSet<>(dependencyInjector.configures(configures));
        injectedInstances.addAll(componentHolders);
    }
}
