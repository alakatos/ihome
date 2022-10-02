package hu.lakati.ihome.hw.kodepic.net.protocol;

import hu.lakati.ihome.hw.kodepic.device.board.Port;
import lombok.Getter;

@Getter
public class DataPacket extends Packet{

    private static final int DATA_LENGTH_PORT_ORDINAL = 1;

    private final int value;
    private final Port port;

    public DataPacket(PacketReader packetReader) throws EHomeProtocolException {

        super(PacketType.DATA, packetReader);

        PortType portType = packetReader.parsePortType();
        int portNumber = packetReader.parseUintValue(DATA_LENGTH_PORT_ORDINAL);
        port = new Port(portType, portNumber);
        
        value = packetReader.parseUintValue(portType.getValueLength());
    }

}
