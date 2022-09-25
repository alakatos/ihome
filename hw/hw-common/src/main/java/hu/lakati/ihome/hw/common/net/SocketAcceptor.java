package hu.lakati.ihome.hw.common.net;

import java.io.IOException;
import java.net.Socket;

@FunctionalInterface
public interface SocketAcceptor {
    void acceptSocket(Socket socket) throws IOException, ProtocolException;
}
