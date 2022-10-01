package hu.lakati.ihome.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import hu.lakati.ihome.common.EventBroker;
import hu.lakati.ihome.hw.kodepic.net.BoardFactory;
import hu.lakati.ihome.hw.kodepic.net.ConnectionHandler;
import hu.lakati.ihome.hw.kodepic.net.IConnectionHandler;
import hu.lakati.ihome.hw.kodepic.net.KodepicConfig;
import hu.lakati.ihome.hw.kodepic.net.board.BoardFactoryImpl;
import hu.lakati.ihome.service.EventBrokerImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EventBroker.class).toInstance(new EventBrokerImpl());
        bind(BoardFactory.class).to(BoardFactoryImpl.class);
        bind(KodepicConfig.class).toInstance(readConfig());
        bind(IConnectionHandler.class).to(ConnectionHandler.class).in(Scopes.SINGLETON);
    }

    private KodepicConfig readConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(getConfigReader(), KodepicConfig.class);
        } catch (FileNotFoundException  e) {
            throw new IllegalArgumentException("Config file not found", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid config", e);
        }
    }

    Reader getConfigReader() throws FileNotFoundException {
        String configFile = System.getProperty("configFile");
        if (configFile != null) {
            log.info("Reading config from file {}", configFile);
            return new FileReader(System.getProperty("configFile"));
        } else {
            log.info("Using default config from classpath");
            return new InputStreamReader(getClass().getClassLoader().getResourceAsStream("default-config.yaml"));
        }
    }
}
