package hu.lakati.ihome.model;

import java.util.function.Consumer;

public interface StateChangedEventBroker extends Consumer<StateChangedEvent> {
}
