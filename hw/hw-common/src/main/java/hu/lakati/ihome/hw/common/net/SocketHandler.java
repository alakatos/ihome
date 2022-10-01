package hu.lakati.ihome.hw.common.net;

import java.io.IOException;
import java.net.Socket;

@FunctionalInterface
public interface SocketHandler {
    void handleSocket(Socket socket) throws IOException, ProtocolException;
}
