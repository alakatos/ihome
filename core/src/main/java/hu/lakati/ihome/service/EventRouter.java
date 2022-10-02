package hu.lakati.ihome.service;

import java.util.function.Consumer;

import hu.lakati.ihome.common.Event;
import hu.lakati.ihome.model.Gadget;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class EventRouter implements Consumer<Event> {

    @Override
    public void accept(Event event) {
        Gadget gadget = findAssociatedGadget(event.getSourceId());
        if (gadget != null) {
            if (gadget.handleEvent(event)) {
                log.debug("Event {} handled by gadget {}", event, gadget);
            } else {
                log.warn("Event {} is NOT handled by gadget {}", event, gadget);
            }
        } else {
            log.warn("gadget with sourceId {} not found", event.getSourceId());
        }
    }
    

    private Gadget findAssociatedGadget(String eventSourceId) {
        //TODO lookup gadget in sourceIdGadget map
        return null;
    }
}
