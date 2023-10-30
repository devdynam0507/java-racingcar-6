package racingcar.framework.event;

import java.util.List;

public interface EventRegistry {

    void registerEvent(Object eventListener);

    List<EventHolder> getEventListener(Class<?> eventType);
}
