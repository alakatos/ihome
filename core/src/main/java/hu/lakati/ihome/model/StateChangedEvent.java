package hu.lakati.ihome.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StateChangedEvent {
    private final long timestamp;
    private Gadget gadget;
    private final State newState;
}
