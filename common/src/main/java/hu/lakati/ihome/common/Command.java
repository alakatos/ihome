package hu.lakati.ihome.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Command {

    private final long timestamp;
    /** The id of the target given in deviceId@endpointId format */
    private final String targetId; 
    
}
