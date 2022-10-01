package hu.lakati.ihome.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Event {
    private final long timestamp;
    private final EventSource eventSource;
    @Setter
    private boolean stopPropagation;

}
