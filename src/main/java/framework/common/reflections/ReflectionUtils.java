package framework.common.reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectionUtils {

    public static List<MethodAnnotationHolder> getMethodsWithAnnotation(
        Object targetClass, Class<? extends Annotation> annotation
    ) {
        Class<?> clazz = targetClass.getClass();
        List<MethodAnnotationHolder> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(annotation)) {
                continue;
            }
            Annotation attachedAnnotation = method.getAnnotation(annotation);
            List<Class<?>> parameterTypes = List.of(method.getParameterTypes());
            List<Parameter> parameters = List.of(method.getParameters());
            MethodAnnotationHolder methodAnnotationHolder =
                    MethodAnnotationHolder.of(targetClass, method, attachedAnnotation, parameterTypes, parameters);
            methods.add(methodAnnotationHolder);
        }
        return methods;
    }

    public static Optional<ConstructorAnnotationHolder<?>> getConstructorWithAnnotation(
        Class<?> clazz, Class<? extends Annotation> annotation
    ) {
        Constructor<?> findConstructor = null;
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (!constructor.isAnnotationPresent(annotation)) {
                continue;
            }
            findConstructor = constructor;
            break;
        }
        if (findConstructor == null) {
            return Optional.empty();
        }
        List<Parameter> parameters = List.of(findConstructor.getParameters());
        List<String> parametersNames = parameters.stream()
                .map(Parameter::getType)
                .map(Class::getSimpleName)
                .toList();
        ConstructorAnnotationHolder<?> annotationHolder =
                ConstructorAnnotationHolder.of(findConstructor, parametersNames, parameters);
        return Optional.of(annotationHolder);
    }
}
