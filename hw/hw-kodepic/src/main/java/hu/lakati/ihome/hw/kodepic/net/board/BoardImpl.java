package hu.lakati.ihome.hw.kodepic.net.board;

import java.io.IOException;

import hu.lakati.ihome.common.Event;
import hu.lakati.ihome.common.EventBroker;
import hu.lakati.ihome.hw.kodepic.net.Board;
import hu.lakati.ihome.hw.kodepic.net.protocol.PacketProtocol;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoardImpl implements Board {

	private final PacketProtocol protocol;
	private final EventBroker eventBroker;

	public BoardImpl(PacketProtocol protocol, EventBroker eventBroker) {
		this.protocol = protocol;
		this.eventBroker = eventBroker;
	}
	
	@Override
	public void run() {
		try {
//TODO read incoming packets and forward them to broker
			Event event = Event.builder()
			.timestamp(System.currentTimeMillis())
			.eventSource(null)
			.build();
			eventBroker.accept(event);
		} finally {
			try {
				protocol.close();
			} catch (IOException e) {
				log.warn("Cannot close protocol", e);;
			}
		}
		
	}

}
