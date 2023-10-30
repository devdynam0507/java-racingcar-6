package racingcar.framework.dependency;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import racingcar.framework.common.reflections.ConstructorAnnotationHolder;
import racingcar.framework.common.reflections.MethodAnnotationHolder;
import racingcar.framework.common.reflections.ReflectionUtils;
import racingcar.framework.common.utils.Strings;
import racingcar.framework.dependency.exceptions.ComponentCandidateException;
import racingcar.framework.dependency.exceptions.ComponentCreationException;
import racingcar.framework.dependency.exceptions.ComponentNotFoundException;
import racingcar.framework.dependency.holder.ComponentHolder;

public class ApplicationContextDependencyInjector implements DependencyInjector {

    private final Map<String, Boolean> componentNameCache = new HashMap<>();

    private ComponentHolder findComponent(Collection<ComponentHolder> componentHolders, Parameter parameter) {
        if (parameter.isAnnotationPresent(Qualifier.class)) {
            Qualifier qualifier = parameter.getAnnotation(Qualifier.class);
            String targetComponentName = qualifier.name();
            Optional<ComponentHolder> candidate = componentHolders.stream()
                   .filter(componentHolder -> componentHolder.componentName() != null)
                   .filter(componentHolder -> componentHolder.componentName().equals(targetComponentName))
                   .findFirst();
            if (candidate.isEmpty()) {
                throw new ComponentNotFoundException("More than one component of the same type was injected.");
            }
            return candidate.get();
        }
        List<ComponentHolder> candidates = componentHolders.stream()
                .filter(component -> parameter.getType().isAssignableFrom(component.getComponentClassType()))
                .toList();
        if (candidates.isEmpty()) {
            throw new ComponentNotFoundException("More than one component of the same type was injected.");
        }
        if (candidates.size() > 1) {
            throw new ComponentCandidateException(
                    "It is difficult to decide because there is more than one of the same "
                    + "component. Use the @Qualifier annotation.");
        }
        return candidates.get(0);
    }

    private void postInjects(List<ComponentHolder> createdInstances, List<MethodAnnotationHolder> injectableHolders) {
        for (MethodAnnotationHolder holder : injectableHolders) {
            Method method = holder.getMethod();
            if (method.getReturnType() == Void.class) {
                continue;
            }
            List<Parameter> parameters = holder.getParameters();

            Object[] arguments = new Object[parameters.size()];
            int index = 0;
            for (Parameter parameter : parameters) {
                arguments[index++] = findComponent(createdInstances, parameter).instantiatedComponent();
            }
            try {
                Component component = method.getAnnotation(Component.class);
                Object newInstance = method.invoke(holder.getMemberInstanceOfMethod(), arguments);
                ComponentHolder componentHolder = new ComponentHolder(component.name(), newInstance);
                createdInstances.add(componentHolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<ComponentHolder> configures(List<Object> configurationObjects) {
        List<MethodAnnotationHolder> methodHolders = new ArrayList<>();
        configurationObjects.forEach(instance -> {
            if (!instance.getClass().isAnnotationPresent(Configuration.class)) {
                return;
            }
            List<MethodAnnotationHolder> methodsWithAnnotation =
                ReflectionUtils.getMethodsWithAnnotation(instance, Component.class);
            methodHolders.addAll(methodsWithAnnotation);
        });

        List<ComponentHolder> createdInstances = new ArrayList<>();
        List<MethodAnnotationHolder> injectableHolders = new ArrayList<>();
        for (MethodAnnotationHolder methodHolder : methodHolders) {
            if (methodHolder.getParameterTypes().size() > 0) {
                injectableHolders.add(methodHolder);
                continue;
            }
            Method method = methodHolder.getMethod();
            if (method.getReturnType() == Void.class) {
                continue;
            }
            ComponentHolder componentHolder = createComponent(methodHolder);
            createdInstances.add(componentHolder);
        }
        postInjects(createdInstances, injectableHolders);

        return createdInstances;
    }

    @Override
    public void injects(List<Class<?>> injectTargets, Set<ComponentHolder> createdInstances) {
        for (Class<?> targetClass : injectTargets) {
            Optional<ConstructorAnnotationHolder<?>> constructor =
                    ReflectionUtils.getConstructorWithAnnotation(targetClass, Inject.class);
            if (constructor.isEmpty()) {
                throw new ComponentCreationException(
                        "The component constructor you want to register must have one @Inject annotation.");
            }
            ConstructorAnnotationHolder<?> constructorHolder = constructor.get();
            List<Parameter> parameters = constructorHolder.getParameters();
            int constructorSize = parameters.size();
            Object[] params = new Object[constructorSize];
            for (int i = 0; i < constructorSize; i++) {
                Parameter constructorParameter = parameters.get(i);
                ComponentHolder component = findComponent(createdInstances, constructorParameter);
                params[i] = component.instantiatedComponent();
            }
            ComponentHolder component = createComponent(constructorHolder, params);
            createdInstances.add(component);
        }
    }

    ComponentHolder createComponent(MethodAnnotationHolder methodHolder) {
        try {
            Method method = methodHolder.getMethod();
            Component component = (Component) methodHolder.getAnnotation();
            Object newInstance = method.invoke(methodHolder.getMemberInstanceOfMethod());
            String componentName = component.name();
            if (componentName == null || componentName.isBlank()) {
                componentName = Strings.toLowerCaseFirstLetter(method.getName());
            }
            if (componentNameCache.containsKey(componentName)) {
                throw new ComponentCreationException("component of '" + componentName + "' already exists.");
            }
            ComponentHolder componentHolder = new ComponentHolder(componentName, newInstance);
            componentNameCache.put(componentName, true);
            return componentHolder;
        } catch (IllegalAccessException | InvocationTargetException e2) {
            return null;
        }
    }

    ComponentHolder createComponent(ConstructorAnnotationHolder<?> constructorHolder, Object... params) {
        try {
            Object instance = constructorHolder.getConstructor().newInstance(params);
            String componentName = Strings.toLowerCaseFirstLetter(instance.getClass().getSimpleName());
            componentNameCache.put(componentName, true);
            return new ComponentHolder(componentName, instance);
        } catch (Exception e) {
            return null;
        }
    }
}
