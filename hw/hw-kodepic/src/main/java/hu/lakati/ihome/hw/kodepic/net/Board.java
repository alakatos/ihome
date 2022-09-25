package hu.lakati.ihome.hw.kodepic.net;

import java.io.IOException;

import hu.lakati.ihome.hw.kodepic.net.protocol.PacketProtocol;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Board implements Runnable {

	private PacketProtocol protocol;

	public Board(PacketProtocol protocol) {
		this.protocol = protocol;
	}
	
	@Override
	public void run() {
		try {
//TODO read incoming packets and forward them to broker
		} finally {
			try {
				protocol.close();
			} catch (IOException e) {
				log.warn("Cannot close protocol", e);;
			}
		}
		
	}

}
