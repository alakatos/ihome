package hu.lakati.ihome.hw.kodepic.net.protocol;

public class PacketFactory {
	public static Packet createPacket(byte[] data) throws EHomeProtocolException {
		PacketReader packetReader = new PacketReader(data);
		
		Packet packet = null;
		switch (packetReader.getPacketType()) {
			case STARTUP:
				packet = new StartupPacket(packetReader);
				break;
			case ALIVE:
				packet = new AlivePacket(packetReader);
				break;
			case DATA:
				packet = new DataPacket(packetReader);
				break;

			default:
				packet = new Packet(packetReader.getPacketType(), packetReader);
		}
		return packet;
	}
}
