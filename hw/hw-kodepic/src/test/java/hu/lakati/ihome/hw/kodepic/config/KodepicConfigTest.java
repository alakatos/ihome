package hu.lakati.ihome.hw.kodepic.config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

import hu.lakati.ihome.hw.common.net.MacAddress;
import hu.lakati.ihome.hw.kodepic.device.board.BoardAlias;
import hu.lakati.ihome.hw.kodepic.device.board.BoardRegistry;
import hu.lakati.ihome.hw.kodepic.device.board.BoardType;

public class KodepicConfigTest {
    @Test
    public void testBuilder() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));
        KodepicConfig config = KodepicConfig.builder()
        .localUdpListenerPort(8889)
        .tcpServerPort(8888)
        .maxConnections(20)
        .boardRegistry(
            BoardRegistry.builder().registry(
                Stream.of(new Object [][] {
                    { new MacAddress("0F-01-02-03-04-05"), new BoardAlias("myRelay", BoardType.RELAY) }
                }).collect(Collectors.toMap(data -> (MacAddress)data[0], data -> (BoardAlias)data[1]))).build()
        ).build();

        
        String output = mapper.writeValueAsString(config);
        System.out.println(output);
        assertThat(output, containsString("boardType: \"RELAY\""));
    }
}
