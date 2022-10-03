package hu.lakati.ihome.hw.kodepic.net.protocol;

import hu.lakati.ihome.hw.kodepic.device.board.Port;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DataPacket extends Packet {

    private static final int DATA_LENGTH_PORT_ORDINAL = 1;
    private static final int DATA_LENGTH_PORT_TYPE = 2;

    private final Port port;
    private final int value;

    public DataPacket(PacketReader packetReader) throws EHomeProtocolException {

        super(PacketType.DATA, packetReader);

        PortType portType = readPortType(packetReader);
        int portNumber = packetReader.readIntValue(DATA_LENGTH_PORT_ORDINAL);
        port = new Port(portType, portNumber);
        
        value = packetReader.readIntValue(portType.getValueLength());
    }
    
    
    private PortType readPortType(PacketReader packetReader) throws EHomeProtocolException {
        int portTypeId = packetReader.readIntValue(DATA_LENGTH_PORT_TYPE);
        try {
            return PortType.getById(portTypeId);
        } catch (IllegalArgumentException e) {
            throw new EHomeProtocolException(e.getMessage());
        }
    }


}
