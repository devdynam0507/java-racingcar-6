package framework.common.reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class MethodAnnotationHolder {

    private final Object instance;
    private final Method method;
    private final Annotation annotation;
    private final List<Class<?>> parameterTypes;
    private final List<Parameter> parameters;

    private MethodAnnotationHolder(
        Object instance,
        Method method,
        Annotation annotation,
        List<Class<?>> parameterTypes,
        List<Parameter> parameters
    ) {
        this.instance = instance;
        this.method = method;
        this.annotation = annotation;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
    }

    public Object getMemberInstanceOfMethod() {
        return instance;
    }

    public Method getMethod() {
        return method;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public List<Class<?>> getParameterTypes() {
        return parameterTypes;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public static MethodAnnotationHolder of(
        Object instance,
        Method method,
        Annotation annotation,
        List<Class<?>> parameterTypes,
        List<Parameter> parameters
    ) {
        return new MethodAnnotationHolder(instance, method, annotation, parameterTypes, parameters);
    }
}
