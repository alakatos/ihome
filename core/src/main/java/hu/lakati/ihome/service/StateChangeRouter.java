package hu.lakati.ihome.service;

import java.util.function.Consumer;

import hu.lakati.ihome.common.Event;
import hu.lakati.ihome.model.Gadget;
import hu.lakati.ihome.model.StateChangeListener;
import hu.lakati.ihome.model.StateChangedEvent;

public class StateChangeRouter implements Consumer<StateChangedEvent> {

    @Override
    public void accept(StateChangedEvent event) {
        StateChangeListener listener = findAssociatedListener(event.getSourceId());
        if (listener != null) {
            listener.stateChanged(event);
        }
        //TODO forward to parents and their eventHandlers
    }
    

    private StateChangeListener findAssociatedListener(String stateProviderSourceId) {
        return null;
    }
}
