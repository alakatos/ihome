package hu.lakati.ihome.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

import javax.net.ServerSocketFactory;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TCPServer implements Runnable {

	private ServerSocket serverSocket;
	private boolean shouldStop;
	private ConnectionHandler connectionHandler;

	public TCPServer(int port, @NonNull ConnectionHandler connectionHandler) throws IOException {
		this(port, connectionHandler, ServerSocketFactory.getDefault());
	}

	public TCPServer(int port, @NonNull ConnectionHandler connectionHandler,
			@NonNull ServerSocketFactory serverSocketFactory)
			throws IOException {
		this.connectionHandler = connectionHandler;
		this.serverSocket = serverSocketFactory.createServerSocket(port);
		serverSocket.setSoTimeout(500);
		log.info("Server started on port {}", port);
		connectionHandler.setTcpServerPort(port);
	}

	public void stop() {
		shouldStop = true;
	}

	void shutdown() throws IOException {
		serverSocket.close();
	}

	public void run() {
		try {
			while (!shouldStop) {
				try {
					connectionHandler.socketCreated(serverSocket.accept());
				} catch (SocketTimeoutException e) {
					// Nothing to do
					continue;
				} catch (IOException e) {
					log.warn("Could not accept incoming connection. Exiting...", e);
					stop();
				} catch (Exception e) {
					log.warn("Connection handler failed", e);
				}
			}
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				log.warn("Cannot close server socket", e);
			}
			log.info("TCPServer stopped.");
		}
	}

}
