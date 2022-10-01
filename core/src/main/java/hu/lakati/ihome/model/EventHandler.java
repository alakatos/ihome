package hu.lakati.ihome.model;


import hu.lakati.ihome.common.Event;

public interface EventHandler extends StatefulComponent {
    /** Returns true if the state has changed */
    boolean handleEvent(Event event);
}
