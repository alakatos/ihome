package hu.lakati.ihome.app;

import java.io.IOException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import hu.lakati.ihome.hw.common.net.TCPServer;
import hu.lakati.ihome.hw.kodepic.config.KodepicConfig;
import hu.lakati.ihome.hw.kodepic.net.ConnectionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Launcher {
    
    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(new AppModule());
        ConnectionHandler connectionHandler = injector.getInstance(ConnectionHandler.class);
        KodepicConfig config = injector.getInstance(KodepicConfig.class);
        TCPServer tcpServer = new TCPServer(config.getTcpServerPort(), connectionHandler);
      
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.info("Stopping ConnectionHandler and TCPServer");
                connectionHandler.stop();
                tcpServer.stop();
            }
        });

        new Thread(tcpServer).start();
        new Thread(connectionHandler).start();

    }

}
