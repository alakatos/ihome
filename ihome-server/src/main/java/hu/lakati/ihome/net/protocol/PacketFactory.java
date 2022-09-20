package hu.lakati.ihome.net.protocol;

public class PacketFactory {
	public static Packet createPacket(byte[] data) throws EHomeProtocolException {
		PacketType packetType = PacketType.fromData(data);
		try {
			Packet packet = null; 
			switch (packetType) {
			case STARTUP:
				packet = new StartupPacket(data);
				break;
			case ALIVE:
				packet = new AlivePacket(data);
				break;
			case DATA:
				packet = new DataPacket(data);
				break;

            default:
				packet = new Packet(packetType, data);
			}
			return packet;
		} catch (IndexOutOfBoundsException e) { //parse error
			throw new EHomeProtocolException("Packet with incorrect length or subtype index received:" + packetType, e);
		}
	}
}
