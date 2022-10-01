package hu.lakati.ihome.hw.kodepic.net.board;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

import hu.lakati.ihome.hw.common.net.MacAddress;

public class BoardRegistryTest {
    
    BoardRegistry boardRegistry;

    @Before
    public void setup() {
        boardRegistry = BoardRegistry.builder().registry(
            Stream.of(new Object [][] {
                { new MacAddress("0F-01-02-03-04-05"), new BoardAlias("myRelay", BoardType.RELAY) }
            }).collect(Collectors.toMap(data -> (MacAddress)data[0], data -> (BoardAlias)data[1]))).build();
    }

    @Test
    public void testWritable() throws JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));
        String output = mapper.writeValueAsString(boardRegistry);
        System.out.println(output);
        assertThat(output, containsString("boardType: \"RELAY\""));
    }

    @Test
    public void testFindBoardAlias() {
        assertThat(boardRegistry.findBoardAlias(new MacAddress("0F-01-02-03-04-05")), not(nullValue()));
        assertThat(boardRegistry.findBoardAlias(new MacAddress("0F-01-02-03-04-05")).getAlias(), equalTo("myRelay"));
    }
}
