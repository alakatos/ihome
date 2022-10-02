package hu.lakati.ihome.model;

import hu.lakati.ihome.common.Event;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class StateChangedEvent extends Event {
    private final long timestamp;
    private Gadget gadget;
    private final State newState;
}
