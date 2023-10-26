package framework.dependency;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import framework.common.reflections.ConstructorAnnotationHolder;
import framework.common.reflections.MethodAnnotationHolder;
import framework.common.reflections.ReflectionUtils;

public final class ApplicationContext {

    private final Map<String, Object> instances;
    private final Set<Object> injectedInstances;

    ApplicationContext() {
        instances = new HashMap<>();
        injectedInstances = new HashSet<>();
    }

    public <T> T getInstance(Class<T> targetClass) {
        Optional<Object> objectOptional =
            injectedInstances.stream()
                    .filter(instance -> targetClass.isAssignableFrom(instance.getClass()))
                    .findAny();
        if (objectOptional.isEmpty()) {
            return null;
        }
        return (T) objectOptional.get();
    }

    public <T> T inject(Class<T> targetClass) {
        Optional<ConstructorAnnotationHolder<?>> constructor =
                ReflectionUtils.getConstructorWithAnnotation(targetClass, Inject.class);
        if (constructor.isEmpty()) {
            return null;
        }
        ConstructorAnnotationHolder<?> annotationHolder = constructor.get();
        List<String> parameterNames = annotationHolder.getConstructorParameterNames();
        int constructorSize = parameterNames.size();
        Object[] params = new Object[constructorSize];
        for (int i = 0; i < constructorSize; i++) {
            String componentName = parameterNames.get(i);
            componentName =
                    componentName.substring(0, 1).toLowerCase(Locale.ROOT) + componentName.substring(1);
            if (!instances.containsKey(componentName)) {
                throw new IllegalArgumentException("component not registered \"" + componentName + "\"");
            }
            params[i] = instances.get(componentName);
        }
        try {
            T instance = (T) annotationHolder.getConstructor().newInstance(params);
            injectedInstances.add(instance);
            return instance;
        } catch (Exception e) {
            return null;
        }
    }

    public void configure(Object configureInstance) {
        if (!configureInstance.getClass().isAnnotationPresent(Configuration.class)) {
            return;
        }
        List<MethodAnnotationHolder> methods =
                ReflectionUtils.getMethodsWithAnnotation(configureInstance.getClass(), Component.class);
        for (MethodAnnotationHolder holder : methods) {
            Method method = holder.getMethod();
            if (method.getReturnType() == Void.class) {
                continue;
            }
            try {
                Component component = (Component) holder.getAnnotation();
                String componentName = component.name();
                Object newInstance = method.invoke(configureInstance);
                instances.put(componentName, newInstance);
                injectedInstances.add(newInstance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
