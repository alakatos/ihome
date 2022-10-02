package hu.lakati.ihome.hw.kodepic.net.protocol;

import java.util.Date;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Packet {
    
    private final PacketType packetType;
    private Date createDate =  new Date();

    protected Packet(PacketType packetType, Date createDate) {
        this.packetType = packetType;
        this.createDate = createDate;
    }

    protected Packet(PacketType packetType, PacketReader packetReader) throws EHomeProtocolException {
        this(packetType, packetReader.parsePacketDate());
        if (packetReader.getPacketType() != packetType) {
            throw new IllegalArgumentException("Incorrect packet parser provided: " + packetReader.getPacketType());
        }
    }

    	/** Returns the packet in a raw byte array representation. The byte array returned */
	protected byte[] toByteArray() {
		
		byte[] arr = new byte[PacketProtocol.TCP_PACKET_HEADER_LENGTH];
		arr[0] = (byte) (packetType.ordinal()-1);
		long tst = createDate.getTime();
        for (int i=0; i < 4; i++) {
            arr[i+1] = (byte) ((tst >> (i*8)) & 0xff);
        }
		return arr;
	}
}
