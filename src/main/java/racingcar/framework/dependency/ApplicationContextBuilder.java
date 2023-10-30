package racingcar.framework.dependency;

import java.util.ArrayList;
import java.util.List;

public class ApplicationContextBuilder implements DependencyBuilder<ApplicationContextBuilder, ApplicationContext> {

    private final List<Object> configurations;
    private final List<Class<?>> injectTargets;
    private final List<PostInjectListener> injectListeners;

    protected ApplicationContextBuilder() {
        this.configurations = new ArrayList<>();
        this.injectTargets = new ArrayList<>();
        this.injectListeners = new ArrayList<>();
    }

    public static ApplicationContextBuilder builder() {
        return new ApplicationContextBuilder();
    }

    @Override
    public ApplicationContextBuilder configuration(Object configuration) {
        configurations.add(configuration);
        return this;
    }

    @Override
    public ApplicationContextBuilder configurations(Object... configurations) {
        for (Object configuration : configurations) {
            configuration(configuration);
        }
        return this;
    }

    @Override
    public ApplicationContextBuilder inject(Class<?> injectTargetClass) {
        injectTargets.add(injectTargetClass);
        return this;
    }

    @Override
    public ApplicationContextBuilder injects(Class<?>... injectTargetClasses) {
        for (Class<?> injectTargetClass : injectTargetClasses) {
            inject(injectTargetClass);
        }
        return this;
    }

    @Override
    public ApplicationContextBuilder addPostInjectionHandler(PostInjectListener postInjectListener) {
        injectListeners.add(postInjectListener);
        return this;
    }

    public ApplicationContext build() {
        ApplicationContext applicationContext = new ApplicationContext(configurations, injectTargets);
        injectListeners.forEach(postInjectListener -> postInjectListener.onInitialized(applicationContext));
        return applicationContext;
    }
}
