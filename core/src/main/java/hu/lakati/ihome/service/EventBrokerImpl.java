package hu.lakati.ihome.service;

import hu.lakati.ihome.common.Event;
import hu.lakati.ihome.common.EventBroker;
import hu.lakati.ihome.util.PooledConsumerQueue;

public class EventBrokerImpl extends PooledConsumerQueue<Event> implements EventBroker  {

    public EventBrokerImpl() {
        super(() -> new EventRouter());
    }

    @Override
    protected Event createPoisonElement() {
        return Event.builder().build();
    }

}
