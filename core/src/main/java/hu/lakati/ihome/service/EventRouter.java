package hu.lakati.ihome.service;

import java.util.function.Consumer;

import hu.lakati.ihome.common.Event;
import hu.lakati.ihome.common.EventSource;
import hu.lakati.ihome.model.Gadget;

public class EventRouter implements Consumer<Event> {

    @Override
    public void accept(Event event) {
        Gadget gadget = findAssociatedGadget(event.getEventSource());
        if (gadget.handleEvent(event)) {

        }
        //TODO forward to parents and their eventHandlers
        
    }
    

    private Gadget findAssociatedGadget(EventSource eventSource) {
        return null;
    }
}
