package hu.lakati.ihome.hw.kodepic.net.protocol;

public class DataPacket extends Packet{

    public DataPacket(byte[] data) throws EHomeProtocolException {
        super(PacketType.DATA, data);
    }

}
