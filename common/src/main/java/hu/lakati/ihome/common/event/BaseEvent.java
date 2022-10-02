package hu.lakati.ihome.common.event;

import java.util.Date;

import hu.lakati.ihome.common.Event;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder 
public abstract class BaseEvent implements Event {
    private final Date createDate;
    private final String sourceId;
}
