package hu.lakati.ihome.net.protocol;

public class AlivePacket extends Packet{

    public AlivePacket(byte[] data) throws EHomeProtocolException {
        super(PacketType.ALIVE, data);
    }

}
