package hu.lakati.ihome.service;

import hu.lakati.ihome.model.StateChangedEvent;
import hu.lakati.ihome.model.StateChangedEventBroker;
import hu.lakati.ihome.util.PooledConsumerQueue;

public class StateChangedEventBrokerImpl extends PooledConsumerQueue<StateChangedEvent> implements StateChangedEventBroker {

    public StateChangedEventBrokerImpl() {
        super(() -> new StateChangeRouter());
    }

    @Override
    protected StateChangedEvent createPoisonElement() {
        return StateChangedEvent.builder().build();
    }

}
