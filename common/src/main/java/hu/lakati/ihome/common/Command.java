package hu.lakati.ihome.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Command {

    private final long timestamp;
    private final CommandTarget commandTarget;
    
}
