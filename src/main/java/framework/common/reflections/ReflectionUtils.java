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
        Class<?> clazz, Class<? extends Annotation> annotation
    ) {
        List<MethodAnnotationHolder> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(annotation)) {
                continue;
            }
            Annotation attachedAnnotation = method.getAnnotation(annotation);
            List<Class<?>> parameterTypes = List.of(method.getParameterTypes());
            methods.add(MethodAnnotationHolder.of(method, attachedAnnotation, parameterTypes));
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
        List<String> parameters = Stream.of(findConstructor.getParameters())
                  .map(Parameter::getType)
                  .map(Class::getSimpleName)
                  .collect(Collectors.toList());
        ConstructorAnnotationHolder<?> annotationHolder =
                ConstructorAnnotationHolder.of(findConstructor, parameters);
        return Optional.of(annotationHolder);
    }
}
