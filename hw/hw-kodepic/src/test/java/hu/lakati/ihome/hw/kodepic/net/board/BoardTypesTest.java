package hu.lakati.ihome.hw.kodepic.net.board;

import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class BoardTypesTest {

    @Test
    public void testFromTypeName() {
        assertThat(BoardTypes.fromTypeName("DIMMER1"), not(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromTypeNameFails() {
        BoardTypes.fromTypeName("DIMMER_X");
    }

}
