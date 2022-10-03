package hu.lakati.ihome.common.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class DataEvent extends BaseEvent {
    private final double value;
}
