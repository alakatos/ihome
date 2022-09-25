package hu.lakati.ihome.hw.kodepic;

import java.io.IOException;

import hu.lakati.ihome.hw.common.net.TCPServer;
import hu.lakati.ihome.hw.kodepic.net.ConnectionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IHomeLauncher {

	public static void main(String[] args) throws IOException {

		final int tcpPort = 8082;
		final ConnectionHandler connectionHandler = new ConnectionHandler(100, tcpPort, 8889);
		final TCPServer tcpServer = new TCPServer(tcpPort, connectionHandler);

		new Thread(connectionHandler).start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				log.info("Stopping ConnectionHandler and TCPServer");
				connectionHandler.stop();
				tcpServer.stop();
			}
		});
	}

}
