package framework.common.reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class MethodAnnotationHolder {

    private final Method method;
    private final Annotation annotation;
    private final List<Class<?>> parameterTypes;

    private MethodAnnotationHolder(Method method, Annotation annotation, List<Class<?>> parameterTypes) {
        this.method = method;
        this.annotation = annotation;
        this.parameterTypes = parameterTypes;
    }

    public Method getMethod() {
        return method;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public List<Class<?>> getParameters() {
        return parameterTypes;
    }

    public static MethodAnnotationHolder of(Method method, Annotation annotation, List<Class<?>> parameterTypes) {
        return new MethodAnnotationHolder(method, annotation, parameterTypes);
    }
}
