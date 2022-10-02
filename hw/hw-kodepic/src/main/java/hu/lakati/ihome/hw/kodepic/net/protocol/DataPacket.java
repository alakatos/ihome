package hu.lakati.ihome.hw.kodepic.net.protocol;

import hu.lakati.ihome.hw.kodepic.device.board.Port;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DataPacket extends Packet{

    private static final int DATA_LENGTH_PORT_ORDINAL = 1;

    private final Port port;
    private final int value;

    public DataPacket(PacketReader packetReader) throws EHomeProtocolException {

        super(PacketType.DATA, packetReader);

        PortType portType = packetReader.parsePortType();
        int portNumber = packetReader.parseIntValue(DATA_LENGTH_PORT_ORDINAL);
        port = new Port(portType, portNumber);
        
        value = packetReader.parseIntValue(portType.getValueLength());
    }

}
