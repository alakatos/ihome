package hu.lakati.ihome.impl;

import hu.lakati.ihome.common.Event;
import hu.lakati.ihome.common.event.DataEvent;

public interface EventToValueConverter<T> {
    T convertToValue(DataEvent event);
}
