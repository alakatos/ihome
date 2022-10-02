package hu.lakati.ihome.hw.kodepic.device.board;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class BoardTypeTest {
    @Test
    public void testGetPorts() throws JsonProcessingException {
        assertThat(BoardType.RELAY.getPorts(), hasSize(32));
        assertThat(BoardType.DIMMER.getPorts(), hasSize(24));
    }
}
