package hu.lakati.ihome.hw.kodepic.device.board;

import hu.lakati.ihome.hw.kodepic.net.protocol.PortType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Port {
    private final PortType portType;
    private final int  ordinal;
    private final String id;

    public Port(PortType portType, int ordinal) {
        this.portType = portType;
        this.ordinal = ordinal;
        this.id = portType+"_"+ordinal; 
    }

    @Override
    public String toString() {
        return id;
    }
}