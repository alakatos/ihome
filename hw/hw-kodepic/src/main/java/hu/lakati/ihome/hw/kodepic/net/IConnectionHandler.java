package hu.lakati.ihome.hw.kodepic.net;

import hu.lakati.ihome.hw.common.net.SocketHandler;

public interface IConnectionHandler extends Runnable, SocketHandler {
    void stop();
    
}
