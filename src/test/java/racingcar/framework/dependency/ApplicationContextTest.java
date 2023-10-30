package racingcar.framework.dependency;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import racingcar.framework.dependency.exceptions.ComponentCandidateException;
import racingcar.framework.dependency.exceptions.ComponentCreationException;
import racingcar.framework.dependency.exceptions.ComponentNotFoundException;
import racingcar.framework.dependency.objects.AmbiguousQualifierTarget;
import racingcar.framework.dependency.objects.CompositeTarget;
import racingcar.framework.dependency.objects.QualifierTarget;
import racingcar.framework.dependency.objects.Target;
import racingcar.framework.dependency.objects.TestObject;
import racingcar.framework.dependency.objects.TestValueChild;
import racingcar.framework.dependency.objects.TestValueObject;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationContextTest {

    ApplicationContext applicationContext;

    @BeforeEach
    void before() {
        applicationContext = ApplicationContextBuilder.builder().build();
    }

    @Test
    void 컴포넌트_생성_테스트() {
        @Configuration
        final class Config {
            @Component(name = "testObject")
            public TestObject testObject() {
                return new TestObject("testValue");
            }
        }

        Config configuration = new Config();
        applicationContext.configure(List.of(configuration));

        TestObject instance = applicationContext.getInstance(TestObject.class);

        assertEquals(instance.value(), "testValue");
    }

    @Test
    void 같은_이름의_컴포넌트_생성_테스트() {
        @Configuration
        final class Config {
            @Component(name = "testObject")
            public TestObject testObject() {
                return new TestObject("testValue");
            }

            @Component(name = "testObject")
            public TestObject testObject2() {
                return new TestObject("testValue");
            }
        }

        Config configuration = new Config();

        assertThrows(ComponentCreationException.class, () -> applicationContext.configure(List.of(configuration)));
    }

    @Test
    void 컴포넌트_생성_팩토리메소드에_등록_컴포넌트_재_주입_테스트() {
        @Configuration
        final class Config {
            @Component(name = "testObject")
            public TestObject testObject() {
                return new TestObject("testValue");
            }

            @Component(name = "testChild")
            public TestValueObject testObject2(TestObject testObject) {
                return new TestValueObject(testObject.value());
            }
        }

        Config configuration = new Config();
        applicationContext.configure(List.of(configuration));

        TestValueObject testChild = applicationContext.getInstance("testChild");

        assertEquals(testChild.getValue(), "testValue");
    }

    @Test
    void Inject_가_붙어있지_않은_주입_대상_컴포넌트를_생성할_때() {
         assertThrows(ComponentCreationException.class,
                () -> applicationContext.injects(List.of(TestValueObject.class)));
    }

    @Test
    void 등록되지_않은_컴포넌트를_주입하려_할_때() {
        assertThrows(ComponentNotFoundException.class,
                () -> applicationContext.injects(List.of(TestValueChild.class)));
    }

    @Test
    void 주입_대상이_모호한_컴포넌트에_주입하려_할_때() {
        @Configuration
        final class Config {
            @Component
            public TestObject testObject1() {
                return new TestObject("testValue1");
            }

            @Component
            public TestObject testObject2() {
                return new TestObject("testValue2");
            }
        }

        Config config = new Config();
        applicationContext.configure(List.of(config));

        assertThrows(ComponentCandidateException.class,
                     () -> applicationContext.injects(List.of(AmbiguousQualifierTarget.class)));
    }

    @Test
    void 같은_타입의_컴포넌트_생성_테스트() {
        @Configuration
        final class Config {
            @Component
            public TestObject testObject1() {
                return new TestObject("testValue1");
            }

            @Component
            public TestObject testObject2() {
                return new TestObject("testValue2");
            }
        }

        Config configuration = new Config();
        applicationContext.configure(List.of(configuration));

        TestObject object1 = applicationContext.getInstance("testObject1");
        TestObject object2 = applicationContext.getInstance("testObject2");

        assertEquals(object1.value(), "testValue1");
        assertEquals(object2.value(), "testValue2");
    }

    @Test
    void 의존성_주입_테스트() {
        @Configuration
        final class Config {
            @Component
            public TestObject testObject() {
                return new TestObject("testValue1");
            }
        }

        Config configuration = new Config();
        applicationContext.configure(List.of(configuration));
        applicationContext.injects(List.of(Target.class));

        Target target = applicationContext.getInstance(Target.class);

        assertEquals(target.getTarget().value(), "testValue1");
    }

    @Test
    void Qualifier_의존성_주입_테스트() {
        @Configuration
        final class Config {
            @Component
            public TestObject testObject1() {
                return new TestObject("testValue1");
            }

            @Component
            public TestObject testObject2() {
                return new TestObject("testValue2");
            }
        }

        Config configuration = new Config();
        applicationContext.configure(List.of(configuration));
        applicationContext.injects(List.of(QualifierTarget.class));

        QualifierTarget target = applicationContext.getInstance(QualifierTarget.class);

        assertEquals(target.getTarget().value(), "testValue2");
    }

    @Test
    void 상속관계_주입_테스트() {
        @Configuration
        final class Config {
            @Component
            public TestValueObject testObject1() {
                return new TestValueChild("value");
            }
        }

        Config configuration = new Config();
        applicationContext.configure(List.of(configuration));
        applicationContext.injects(List.of(CompositeTarget.class));

        CompositeTarget target = applicationContext.getInstance(CompositeTarget.class);

        assertEquals(target.getTestValueObject().getValue(), "value");
    }
}