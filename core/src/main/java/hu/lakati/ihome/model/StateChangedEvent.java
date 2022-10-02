package hu.lakati.ihome.model;

import hu.lakati.ihome.common.event.BaseEvent;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class StateChangedEvent extends BaseEvent {
    private final State oldState;
    private final State newState;
}
