package hu.lakati.ihome.hw.kodepic.net.protocol;

public class AlivePacket extends Packet{

    public AlivePacket(PacketReader packetReader) throws EHomeProtocolException {
        super(PacketType.ALIVE, packetReader);
    }

}
