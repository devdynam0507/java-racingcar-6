package framework.dependency;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import framework.common.reflections.ConstructorAnnotationHolder;
import framework.common.reflections.MethodAnnotationHolder;
import framework.common.reflections.ReflectionUtils;
import framework.common.utils.Strings;
import framework.dependency.holder.ComponentHolder;

public class ApplicationContextDependencyInjector implements DependencyInjector {

    private ComponentHolder findComponent(Collection<ComponentHolder> componentHolders, Parameter parameter) {
        if (parameter.isAnnotationPresent(Qualifier.class)) {
            Qualifier qualifier = parameter.getAnnotation(Qualifier.class);
            String targetComponentName = qualifier.name();
            Optional<ComponentHolder> candidate = componentHolders.stream()
                   .filter(componentHolder -> componentHolder.getComponentName() != null)
                   .filter(componentHolder -> componentHolder.getComponentName().equals(targetComponentName))
                   .findFirst();
            if (candidate.isEmpty()) {
                throw new IllegalArgumentException("More than one component of the same type was injected.");
            }
            return candidate.get();
        }
        List<ComponentHolder> candidates = componentHolders.stream()
                .filter(component -> parameter.getType().isAssignableFrom(component.getComponentClassType()))
                .toList();
        if (candidates.isEmpty()) {
            throw new IllegalArgumentException("More than one component of the same type was injected.");
        }
        if (candidates.size() > 1) {
            throw new IllegalArgumentException("It is difficult to decide because there is more than one of the same "
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
                arguments[index++] = findComponent(createdInstances, parameter).getInstantiatedComponent();
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
        Map<String, Boolean> componentNameCache = new HashMap<>();
        for (MethodAnnotationHolder methodHolder : methodHolders) {
            if (methodHolder.getParameterTypes().size() > 0) {
                injectableHolders.add(methodHolder);
                continue;
            }
            Method method = methodHolder.getMethod();
            if (method.getReturnType() == Void.class) {
                continue;
            }
            try {
                Component component = (Component) methodHolder.getAnnotation();
                Object newInstance = method.invoke(methodHolder.getMemberInstanceOfMethod());
                String componentName = component.name();
                if (componentName == null || componentName.isBlank()) {
                    componentName = Strings.toLowerCaseFirstLetter(method.getName());
                }
                if (componentNameCache.containsKey(componentName)) {
                    throw new IllegalArgumentException("component of '" + componentName + "' already exists.");
                }
                ComponentHolder componentHolder = new ComponentHolder(componentName, newInstance);
                createdInstances.add(componentHolder);
                componentNameCache.put(componentName, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                continue;
            }
            ConstructorAnnotationHolder<?> constructorHolder = constructor.get();
            List<Parameter> parameters = constructorHolder.getParameters();
            int constructorSize = parameters.size();
            Object[] params = new Object[constructorSize];
            for (int i = 0; i < constructorSize; i++) {
                Parameter constructorParameter = parameters.get(i);
                ComponentHolder component = findComponent(createdInstances, constructorParameter);
                params[i] = component.getInstantiatedComponent();
            }
            try {
                Object instance = constructorHolder.getConstructor().newInstance(params);
                String componentName = Strings.toLowerCaseFirstLetter(instance.getClass().getSimpleName());
                ComponentHolder componentHolder = new ComponentHolder(componentName, instance);
                createdInstances.add(componentHolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
