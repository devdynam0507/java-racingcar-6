package framework.dependency;

import java.util.ArrayList;
import java.util.List;

public class ApplicationContextBuilder {

    private final List<Object> configurations;
    private final List<Class<?>> injectTargets;

    private ApplicationContextBuilder() {
        this.configurations = new ArrayList<>();
        this.injectTargets = new ArrayList<>();
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

    public ApplicationContext build() {
        ApplicationContext applicationContext = new ApplicationContext();
        for (Object configuration : configurations) {
            applicationContext.configure(configuration);
        }
        for (Class<?> injectTargetClass : injectTargets) {
            applicationContext.inject(injectTargetClass);
        }
        return applicationContext;
    }
}
