package hu.lakati.ihome.hw.kodepic.net.protocol;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Packet {
    
    private final PacketType packetType;
    @Setter
    private Date createDate =  new Date();

    protected Packet(PacketType packetType, Date createDate) {
        this.packetType = packetType;
        this.createDate = createDate;
    }

    protected Packet(PacketType packetType, PacketReader packetReader) throws EHomeProtocolException {
        this(packetType, packetReader.readDate());
    }

    	/** Returns the packet in a raw byte array representation. The byte array returned */
	protected void write(PacketWriter writer) {
        writer.writePacketType(packetType);
        writer.writeDate(createDate);
	}
}
