package framework.dependency.holder;

public class ComponentHolder {

    private final String componentName;
    private final Object instantiatedComponent;

    public ComponentHolder(String componentName, Object instantiatedComponent) {
        this.componentName = componentName;
        this.instantiatedComponent = instantiatedComponent;
    }

    public String getComponentName() {
        return componentName;
    }

    public Object getInstantiatedComponent() {
        return instantiatedComponent;
    }

    public Class<?> getComponentClassType() {
        return instantiatedComponent.getClass();
    }

    @Override
    public String toString() {
        return componentName;
    }

    public static ComponentHolder create(String componentName, Object instantiatedComponent) {
        return new ComponentHolder(componentName, instantiatedComponent);
    }
}
