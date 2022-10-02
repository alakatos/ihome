package hu.lakati.ihome.common.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ErrorEvent extends BaseEvent {
    private final String errorDescription;
}
