package hu.lakati.ihome.hw.kodepic.net.protocol;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PacketTest {
    @Mock
    PacketReader packetReaderMock;
    
    @Test
    public void testParse() throws EHomeProtocolException {
        
        when(packetReaderMock.getPacketType()).thenReturn(PacketType.ALIVE);
        new AlivePacket(packetReaderMock);

        verify(packetReaderMock).parsePacketDate();
    }
}
