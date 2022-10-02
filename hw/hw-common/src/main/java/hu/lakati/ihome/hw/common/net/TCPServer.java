package hu.lakati.ihome.hw.common.net;

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
	private SocketHandler socketAcceptor;

	public TCPServer(int port, @NonNull SocketHandler socketAcceptor) throws IOException {
		this(port, socketAcceptor, ServerSocketFactory.getDefault());
	}

	public TCPServer(int port, @NonNull SocketHandler socketAcceptor,
			@NonNull ServerSocketFactory serverSocketFactory)
			throws IOException {
		this.socketAcceptor = socketAcceptor;
		this.serverSocket = serverSocketFactory.createServerSocket(port);
		serverSocket.setSoTimeout(500);
		log.info("Server started on port {}", port);
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
					socketAcceptor.handleSocket(serverSocket.accept());
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
