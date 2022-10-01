package hu.lakati.ihome.model;

public interface StateRepo {
    State getState(Gadget gadget);
    void updateState(Gadget gadget);
}
