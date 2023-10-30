package racingcar.framework.common.reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

import java.util.List;

public class ConstructorAnnotationHolder<T> {

    private final Constructor<T> constructor;
    private final List<String> parameterNames;
    private final List<Parameter> parameters;

    public ConstructorAnnotationHolder(
        Constructor<T> constructor,
        List<String> parameterNames,
        List<Parameter> parameters
    ) {
        this.constructor = constructor;
        this.parameterNames = parameterNames;
        this.parameters = parameters;
    }

    public Constructor<T> getConstructor() {
        return constructor;
    }

    public List<String> getConstructorParameterNames() {
        return parameterNames;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public static <U> ConstructorAnnotationHolder<U> of(
        Constructor<U> constructor,
        List<String> parameterNames,
        List<Parameter> parameters
    ) {
        return new ConstructorAnnotationHolder<U>(constructor, parameterNames, parameters);
    }
}
