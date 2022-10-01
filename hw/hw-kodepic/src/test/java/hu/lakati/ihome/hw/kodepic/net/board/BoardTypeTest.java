package hu.lakati.ihome.hw.kodepic.net.board;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasSize;
import com.fasterxml.jackson.core.JsonProcessingException;

public class BoardTypeTest {
    @Test
    public void testGetPorts() throws JsonProcessingException {
        assertThat(BoardType.RELAY.getPorts(), hasSize(32));
        assertThat(BoardType.DIMMER.getPorts(), hasSize(24));
    }
}
