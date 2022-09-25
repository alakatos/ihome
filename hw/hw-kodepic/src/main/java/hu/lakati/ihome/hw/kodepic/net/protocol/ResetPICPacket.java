package hu.lakati.ihome.hw.kodepic.net.protocol;

import java.util.Date;

public class ResetPICPacket extends Packet{

    public ResetPICPacket() {
        super(PacketType.RESETPIC, new Date());
    }

}
