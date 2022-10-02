package hu.lakati.ihome.common.event.command;

import java.util.Date;

import hu.lakati.ihome.common.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class DataCommand implements Command {
    private final Date createDate;
    private final String targetId;
    @Builder.Default
    private final Object value = 0;
}
