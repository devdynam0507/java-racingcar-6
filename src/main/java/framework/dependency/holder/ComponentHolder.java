package framework.dependency.holder;

public record ComponentHolder(String componentName, Object instantiatedComponent) {

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
