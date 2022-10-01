package hu.lakati.ihome.common;

public interface ControlContext {
    void sendCommand(Command command, CommandTarget target);
}
