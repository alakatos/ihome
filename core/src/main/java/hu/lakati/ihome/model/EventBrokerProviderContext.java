package hu.lakati.ihome.model;

import hu.lakati.ihome.common.EventBroker;

public interface EventBrokerProviderContext {
    EventBroker getEventBroker();
}
