package hu.lakati.ihome.net;

import java.io.IOException;
import java.net.Socket;

import hu.lakati.ihome.net.protocol.PacketProtocol;
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
//TODO sthg.
		} finally {
			try {
				protocol.close();
			} catch (IOException e) {
				log.warn("Cannot close protocol", e);;
			}
		}
		
	}

}
