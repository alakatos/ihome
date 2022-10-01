package hu.lakati.ihome.model;

import java.util.function.Consumer;

import hu.lakati.ihome.common.Command;

public interface CommandBroker extends Consumer<Command> {
    
}
