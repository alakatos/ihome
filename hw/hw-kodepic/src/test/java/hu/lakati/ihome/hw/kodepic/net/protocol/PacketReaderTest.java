package hu.lakati.ihome.hw.kodepic.net.protocol;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;

import org.junit.Test;

import hu.lakati.ihome.hw.common.net.MacAddress;

public class PacketReaderTest {


    @Test
    public void testGetPacketType() throws NumberFormatException, EHomeProtocolException {
        PacketReader reader = new PacketReader(ByteArrayUtil.parseHexString("0301000000"));
        assertThat(reader.getPacketType(), equalTo(PacketType.ALIVE));
        assertThat(reader.parsePacketDate(), equalTo(new Date(1)));
    }

    @Test
    public void testParseMacAddress() throws NumberFormatException, EHomeProtocolException {
        PacketReader reader = new PacketReader(ByteArrayUtil.parseHexString("020A0B0C0D0E0F"));
        assertThat(reader.getPacketType(), equalTo(PacketType.STARTUP));
        assertThat(reader.parseMacAddress(), equalTo(new MacAddress("0A-0B-0C-0D-0E-0F")));
    }

    @Test
    public void testParsePacketDate() {

    }

    @Test
    public void testParsePortType() {

    }

    @Test
    public void testParseUintValue() {

    }

    @Test
    public void testReadRemainingAsString() {

    }
}
