package racingcar.framework.dependency;

public interface DependencyBuilder<Builder, Created> {

    Builder configuration(Object configuration);

    Builder configurations(Object... configurations);

    Builder inject(Class<?> clazz);

    Builder injects(Class<?>... clazz);

    Builder addPostInjectionHandler(PostInjectListener postInjectListener);

    Created build();
}
