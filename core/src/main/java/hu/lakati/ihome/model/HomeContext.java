package hu.lakati.ihome.model;

import java.util.List;

public interface HomeContext {
    Location getLocation(Gadget gadget);
    Gadget lookupGadgetBySourceId(String sourceId);
    Gadget lookupGadgetByPath(String path);
    List<StateChangeListener> lookupStateChangedSubscribers(StateChangedEvent event);
}
