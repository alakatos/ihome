package hu.lakati.ihome.hw.kodepic.net.protocol;

public class PacketFactory {
	public static Packet createPacket(PacketReader packetReader) throws EHomeProtocolException {
		
		Packet packet = null;
		PacketType packetType =  packetReader.readPacketType();
		switch (packetType) {
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
				packet = new Packet(packetType, packetReader);
		}
		return packet;
	}
}
