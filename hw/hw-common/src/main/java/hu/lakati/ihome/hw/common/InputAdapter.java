package hu.lakati.ihome.hw.common;

import org.w3c.dom.events.Event;

public interface InputAdapter<T> {
    Event convertToEvent(T lowLevelData);
}