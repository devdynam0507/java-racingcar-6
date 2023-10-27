package framework.dependency.holder;

import java.util.List;

import framework.common.reflections.MethodAnnotationHolder;

public class ConfigurationHolder {

    private final Object configurationInstance;
    private final List<MethodAnnotationHolder> methods;

    private ConfigurationHolder(Object configurationInstance, List<MethodAnnotationHolder> methods) {
        this.configurationInstance = configurationInstance;
        this.methods = methods;
    }

    public Object getConfigurationInstance() {
        return configurationInstance;
    }

    public List<MethodAnnotationHolder> getMethods() {
        return methods;
    }

    public static ConfigurationHolder create(
        Object configurationInstance, List<MethodAnnotationHolder> methods
    ) {
        return new ConfigurationHolder(configurationInstance, methods);
    }
}
