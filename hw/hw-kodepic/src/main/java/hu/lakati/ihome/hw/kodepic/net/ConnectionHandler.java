package hu.lakati.ihome.hw.kodepic.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import hu.lakati.ihome.common.EventBroker;
import hu.lakati.ihome.hw.common.net.ProtocolException;
import hu.lakati.ihome.hw.kodepic.net.protocol.ByteArrayUtil;
import hu.lakati.ihome.hw.kodepic.net.protocol.ChecksumUtil;
import hu.lakati.ihome.hw.kodepic.net.protocol.EHomeProtocolException;
import hu.lakati.ihome.hw.kodepic.net.protocol.Packet;
import hu.lakati.ihome.hw.kodepic.net.protocol.PacketProtocol;
import hu.lakati.ihome.hw.kodepic.net.protocol.StartupPacket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionHandler implements IConnectionHandler {

	private static final String PACKET_TYPE_ALIVE = "ALIVE";
	private static final String PACKET_TYPE_CONNECT = "CONNECT";

	private DatagramSocket listenerSocket;
	private DatagramSocket senderSocket;

	private final BoardFactory boardFactory;
	private final EventBroker eventBroker;

	private final int tcpServerPort;
	private final int localUdpListenerPort;

	private ExecutorService executorService;
	private boolean shouldStop;

	@Inject
	public ConnectionHandler(KodepicConfig config, BoardFactory boardFactory, EventBroker eventBroker) throws IOException {
		this(
			config.getLocalUdpListenerPort(), 
			config.getTcpServerPort(),
			boardFactory,
			eventBroker,
			Executors.newFixedThreadPool(config.getMaxConnections()));
	}

	ConnectionHandler(int localUdpListenerPort, int tcpServerPort, BoardFactory boardFactory, EventBroker eventBroker, ExecutorService executorService) throws IOException {
		this.executorService = executorService;
		this.tcpServerPort = tcpServerPort;
		this.localUdpListenerPort = localUdpListenerPort;
		this.boardFactory = boardFactory;
		this.eventBroker = eventBroker;
		startUDP();
	}

	private void startUDP() throws SocketException {
		listenerSocket = new DatagramSocket(null);
		listenerSocket.setBroadcast(true);
		listenerSocket.bind(new InetSocketAddress(localUdpListenerPort));
		senderSocket = new DatagramSocket();
	}

	public void handleSocket(Socket socket) throws IOException, ProtocolException {
		PacketProtocol protocol = new PacketProtocol(socket);
		boolean threadSpawned = false;
		try {
			Packet packet = protocol.readPacket();
			if (!(packet instanceof StartupPacket)) {
				throw new EHomeProtocolException("First message has to be a StartupPacket");
			}
			StartupPacket startupPacket = (StartupPacket) packet;
			if (connectSent(startupPacket.getMacAddress())) {
				clearPendingConnect(startupPacket.getMacAddress());
				log.info("Board connected: {}", startupPacket);
				//TODO find board setup based on the content of startupPacket
				executorService.submit(boardFactory.createBoard(protocol, eventBroker));
				threadSpawned = true;
			} else {
				log.warn("Refusing unexpected board: {}", startupPacket);
			}
		} finally {
			if (!threadSpawned) {
				protocol.close();
			}
		}
	}

	private void clearPendingConnect(MacAddress macAddress) {
		pendingConnectMap.remove(macAddress);
	}

	private Map<MacAddress, Date> pendingConnectMap = new HashMap<>();

	private boolean connectSent(MacAddress macAddress) {
		return pendingConnectMap.containsKey(macAddress);
	}

	private void sendConnect(int serverTcpPort, SocketAddress targetAddress) throws IOException {
		send(ByteArrayUtil.concatByteArrays(PACKET_TYPE_CONNECT.getBytes(),
				ByteArrayUtil.intToByteArray(tcpServerPort)),
				targetAddress);
		log.info("CONNECT:" + serverTcpPort + " sent to " + targetAddress);
	}

	private void send(byte[] data, SocketAddress targetAddress) throws IOException {
		byte[] data2Send = ByteArrayUtil.concatByteArrays(data, new byte[] { ChecksumUtil.countChecksum(data) });
		DatagramPacket dgPacket = new DatagramPacket(data2Send, data2Send.length, targetAddress);
		senderSocket.send(dgPacket);
	}

	public void stop() {
		shouldStop = true;
	}

	/** Keeps listening to ALIVE UDP messages and sends a CONNECT in response, if the given MAC address is registered */
	@Override
	public void run() {
		try {
			log.info("ConnectionHandler started on UDP listener port {}", localUdpListenerPort);
			while (!shouldStop) {
				try {
					DatagramPacket packet = readDatagramPacket();
					byte[] data = getData(packet);
					SocketAddress remoteAddress = packet.getSocketAddress();

					if (ChecksumUtil.isChecksumOK(data) && ByteArrayUtil.startsWith(data, PACKET_TYPE_ALIVE)) {
						int clientUdpPort = ByteArrayUtil.parseInt(data, PACKET_TYPE_ALIVE.length());
						MacAddress clientMacAddress = new MacAddress(data, PACKET_TYPE_ALIVE.length() + 2);
						InetAddress inetAddr = ((InetSocketAddress) remoteAddress).getAddress();
						SocketAddress targetAddress = new InetSocketAddress(inetAddr, clientUdpPort);
						//TODO refuse to send CONNECT if clientMacAddress is not registered 
						log.info("Sending CONNECT to board having MAC address {} on IP {}", clientMacAddress, inetAddr);
						Date firstSentDate = pendingConnectMap.putIfAbsent(clientMacAddress, new Date());
						if (firstSentDate != null) { // already sent
							// TODO log e.g. the 3rd occurance only
							log.warn("Board with MacAddress does not react to CONNECT since: "
									+ new SimpleDateFormat("HH:mm").format(firstSentDate));
						}
						sendConnect(tcpServerPort, targetAddress);
					}
				} catch (SocketTimeoutException e) {
					// do nothing
				}
			}
		} catch (IOException e) {
			log.warn("Cannot read/write UPD packet. Exiting...", e);
		} finally {
			log.info("ConnectionHandler stopped");
			closeUdpListenerSocket();
		}
	}

	private void closeUdpListenerSocket() {
		if (!listenerSocket.isClosed()) {
			listenerSocket.close();
		}
	}

	private byte[] getData(DatagramPacket packet) {
		byte[] data = new byte[packet.getLength()];
		System.arraycopy(packet.getData(), 0, data, 0, data.length);
		return data;
	}

	private DatagramPacket readDatagramPacket() throws IOException {
		byte[] dataBuf = new byte[256];
		DatagramPacket dgPacket = new DatagramPacket(dataBuf, dataBuf.length);
		listenerSocket.receive(dgPacket);
		log.debug("UDP packet received from " + dgPacket.getSocketAddress());
		return dgPacket;
	}

}
