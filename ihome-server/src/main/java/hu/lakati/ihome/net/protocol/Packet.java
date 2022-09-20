package hu.lakati.ihome.net.protocol;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Packet {
    private PacketType packetType;
    @Setter(AccessLevel.PACKAGE)
    private Date createDate;

    protected Packet(PacketType packetType, byte[] data) throws EHomeProtocolException {
        this.packetType = packetType;
        parseData(data);
    }

    protected Packet(PacketType packetType, Date createDate) {
        this.packetType = packetType;
        this.createDate = createDate;
    }

    /** Parses the values from the data byte array. Returns the modified offset value. */
	protected int parseData(byte[] data) throws EHomeProtocolException {
        
        PacketType packetType = PacketType.fromData(data);
        if (this.packetType != null && this.packetType != packetType) {
            throw new EHomeProtocolException("Data does not belong to packet type " +this.packetType + " but to " + packetType);
        }
        this.packetType = packetType;

        //!!! unusable date value (4 bytes) parsing will be skipped
        // long timestamp = 0;
        // for (int i=0; i < 4; i++) { 
        //     long bVal = Byte.toUnsignedInt(data[i+1]); //0th byte was the packetType
        //     timestamp += bVal << (i*8); 
        // }
        // createDate = new Date(timestamp);
        createDate = new Date();
		return PacketProtocol.TCP_PACKET_HEADER_LENGTH;
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
