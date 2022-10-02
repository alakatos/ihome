package hu.lakati.ihome.common;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Event {
    private final long timestamp;
    private final String sourceId;
}
