package hu.lakati.ihome.hw.kodepic.device.board;

import java.io.IOException;
import java.util.Date;

import hu.lakati.ihome.common.Command;
import hu.lakati.ihome.common.DeviceListener;
import hu.lakati.ihome.common.Event;
import hu.lakati.ihome.common.EventBroker;
import hu.lakati.ihome.common.event.DataEvent;
import hu.lakati.ihome.hw.kodepic.Board;
import hu.lakati.ihome.hw.kodepic.net.protocol.DataPacket;
import hu.lakati.ihome.hw.kodepic.net.protocol.EHomeProtocolException;
import hu.lakati.ihome.hw.kodepic.net.protocol.Packet;
import hu.lakati.ihome.hw.kodepic.net.protocol.PacketProtocol;
import hu.lakati.ihome.hw.kodepic.net.protocol.ResetPICPacket;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoardImpl implements Board {

	private final PacketProtocol protocol;
	private final EventBroker eventBroker;
	@Getter
	private final BoardType boardType;
	@Getter
	private final String name;

	private final DeviceListener deviceListener;

	public BoardImpl(PacketProtocol protocol, EventBroker eventBroker, DeviceListener deviceListener, BoardAlias alias) {
		this.protocol = protocol;
		this.eventBroker = eventBroker;
		this.boardType = alias.getBoardType();
		this.deviceListener = deviceListener;
		this.name = alias.getAlias();
	}

	private boolean shouldStop;

	void resetBoard() {
		shouldStop = true;
		if (!protocol.isSocketClosed()) {
			try {
				protocol.writePacket(new ResetPICPacket());
			} catch (IOException e) {
				log.warn("Cannot reset board", e);
			}
		}
	}

	@Override
	public void run() {
		try {
			deviceListener.deviceAdded(this);
			while (!shouldStop) {
				Packet packet = protocol.readPacket();
				switch(packet.getPacketType()) {
					
					case ALIVE: 
						log.debug("Alive packet received");
						break; 

					case DATA: 
					DataPacket dataPacket = (DataPacket) packet;
						log.debug("Data packet received");
						Event event = DataEvent.builder()
						.createDate(new Date())
						.sourceId(dataPacket.getPort().getId()+"@"+name)
						.value(dataPacket.getValue())
						.build();

						eventBroker.accept(event);
						break; 
					
					default:
						log.warn("Unhandled packet: {}", packet);

				}
			}
		} catch (EHomeProtocolException e) {
			log.error("Protocol violated", e);
		} catch (IOException e) {
			log.error("IOException occured", e);
		} finally {
			resetBoard();
			deviceListener.deviceRemoved(this);
			try {
				protocol.close();
			} catch (IOException e) {
				log.warn("Cannot close protocol", e);
			}
		}

	}

	@Override
	public void accept(Command command) {
		command.getTargetId();
	}

}
