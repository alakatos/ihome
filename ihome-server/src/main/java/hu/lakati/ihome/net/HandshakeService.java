package hu.lakati.ihome.net;

import java.net.Socket;

public interface HandshakeService {
	void performHandshake(Socket socket);
}
