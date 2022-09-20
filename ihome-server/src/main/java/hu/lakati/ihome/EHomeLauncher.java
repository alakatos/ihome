package hu.lakati.ihome;

import java.io.IOException;

import hu.lakati.ihome.net.ConnectionHandler;
import hu.lakati.ihome.net.TCPServer;

public class EHomeLauncher {
	
	static void launch() throws IOException {
		
		final ConnectionHandler connectionHandler = new ConnectionHandler(8889, 100);
		final TCPServer tcpServer = new TCPServer(8888, connectionHandler);
		new Thread(tcpServer).start();
		new Thread(connectionHandler).start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				connectionHandler.stop();
				tcpServer.stop();
			}
		});
	}

}
