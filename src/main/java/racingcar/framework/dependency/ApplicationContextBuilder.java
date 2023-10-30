package racingcar.framework.dependency;

import java.util.ArrayList;
import java.util.List;

public class ApplicationContextBuilder {

    private final List<Object> configurations;
    private final List<Class<?>> injectTargets;
    private final List<PostInjectListener> injectListeners;

    private ApplicationContextBuilder() {
        this.configurations = new ArrayList<>();
        this.injectTargets = new ArrayList<>();
        this.injectListeners = new ArrayList<>();
    }

    public static ApplicationContextBuilder builder() {
        return new ApplicationContextBuilder();
    }

    public ApplicationContextBuilder configuration(Object object) {
        configurations.add(object);
        return this;
    }

    public ApplicationContextBuilder inject(Class<?> injectTargetClass) {
        injectTargets.add(injectTargetClass);
        return this;
    }

    public ApplicationContextBuilder addListener(PostInjectListener postInjectListener) {
        injectListeners.add(postInjectListener);
        return this;
    }

    public ApplicationContext build() {
        ApplicationContext applicationContext = new ApplicationContext(configurations, injectTargets);
        injectListeners.forEach(postInjectListener -> postInjectListener.onInitialized(applicationContext));
        return applicationContext;
    }
}
