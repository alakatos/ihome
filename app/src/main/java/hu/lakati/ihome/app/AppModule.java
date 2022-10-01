package hu.lakati.ihome.app;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import hu.lakati.ihome.common.EventBroker;
import hu.lakati.ihome.hw.kodepic.net.BoardFactory;
import hu.lakati.ihome.hw.kodepic.net.ConnectionHandler;
import hu.lakati.ihome.hw.kodepic.net.IConnectionHandler;
import hu.lakati.ihome.hw.kodepic.net.KodepicConfig;
import hu.lakati.ihome.hw.kodepic.net.board.BoardFactoryImpl;
import hu.lakati.ihome.service.EventBrokerImpl;

public class AppModule extends AbstractModule {
    
    @Override
    protected void configure() {
        bind(EventBroker.class).toInstance(new EventBrokerImpl());
        bind(BoardFactory.class).to(BoardFactoryImpl.class);
        bind(KodepicConfig.class).toInstance(
            KodepicConfig.builder()
            .localUdpListenerPort(8889)
            .tcpServerPort(8888)
            .maxConnections(20)
            .build());

        bind(IConnectionHandler.class).to(ConnectionHandler.class).in(Scopes.SINGLETON);
        }
}
