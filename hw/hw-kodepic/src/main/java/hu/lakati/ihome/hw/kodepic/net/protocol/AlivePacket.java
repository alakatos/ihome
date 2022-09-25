package hu.lakati.ihome.hw.kodepic.net.protocol;

public class AlivePacket extends Packet{

    public AlivePacket(byte[] data) throws EHomeProtocolException {
        super(PacketType.ALIVE, data);
    }

}
