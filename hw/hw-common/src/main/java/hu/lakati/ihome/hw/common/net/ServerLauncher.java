package hu.lakati.ihome.hw.common.net;

import java.io.IOException;

@FunctionalInterface
public interface ServerLauncher {
    void launchServer(int port) throws IOException;
}
