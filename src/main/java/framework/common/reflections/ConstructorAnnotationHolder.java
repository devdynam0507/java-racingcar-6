package framework.common.reflections;

import java.lang.reflect.Constructor;
import java.util.List;

public class ConstructorAnnotationHolder<T> {

    private final Constructor<T> constructor;
    private final List<String> parameterNames;

    public ConstructorAnnotationHolder(Constructor<T> constructor, List<String> parameterNames) {
        this.constructor = constructor;
        this.parameterNames = parameterNames;
    }

    public Constructor<T> getConstructor() {
        return constructor;
    }

    public List<String> getConstructorParameterNames() {
        return parameterNames;
    }

    public static <U> ConstructorAnnotationHolder<U> of(Constructor<U> constructor, List<String> parameterNames) {
        return new ConstructorAnnotationHolder<U>(constructor, parameterNames);
    }
}
