package racingcar.framework.event;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import racingcar.framework.event.exceptions.EventListenerRegistrationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleEventDispatcherTest {

    EventPublisher eventPublisher;

    @BeforeEach
    void before() {
        eventPublisher = new SimpleEventDispatcher();
    }

    @Test
    void 이벤트_리스너에_EventPublisher가_없는_경우() {
        final class Events {
            @EventListener
            void onTestEvent(Void voidEvent) {}
        }

        assertThrows(EventListenerRegistrationException.class, () -> eventPublisher.registerEvent(new Events()));
    }

    @Test
    void 이벤트_리스너에서_에러가_발생한_경우() {
        final class Events {
            @EventListener
            void onTestEvent(String event, EventPublisher eventPublisher) {
                throw new IllegalArgumentException("occurred event listener error");
            }
        }
        eventPublisher.registerEvent(new Events());

        assertThrows(IllegalArgumentException.class, () -> eventPublisher.dispatch("test event"));
    }

    @Test
    void 정상적인_이벤트_리스너() {
        OutputStream captor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captor));

        final class Events {
            @EventListener
            void onTestEvent(String event, EventPublisher eventPublisher) {
                System.out.println(event);
            }
        }
        eventPublisher.registerEvent(new Events());

        eventPublisher.dispatch("test event");

        String result = captor.toString().trim();
        assertEquals(result, "test event");
    }

    @Test
    void 연쇄_이벤트_리스너() {
        OutputStream captor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captor));

        final class Event1 {
            @EventListener
            void onTestEvent(String event, EventPublisher eventPublisher) {
                System.out.println(event);
                eventPublisher.dispatch(1);
            }
        }
        final class Event2 {
            @EventListener
            void onTestEvent(Integer event, EventPublisher eventPublisher) {
                System.out.println(event);
            }
        }
        eventPublisher.registerEvent(new Event1());
        eventPublisher.registerEvent(new Event2());

        eventPublisher.dispatch("test event");

        String result = captor.toString().trim();
        assertThat(result).contains("test event", "1");
    }
}