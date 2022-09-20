package hu.lakati.ihome.net.protocol;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class PacketTest {
    @Test
    public void testParse() throws EHomeProtocolException {
        byte[] data = ByteArrayUtil.parseHexString("030000000f");
        Packet packet = new AlivePacket(data);
        System.out.println(packet.getCreateDate().getTime());
        System.out.println(new Date().getTime());
        System.out.println(Long.valueOf(new Date().getTime()).intValue());
        Date what = new Date(Long.valueOf(new Date().getTime()).intValue());
        System.out.println(new SimpleDateFormat("yyyy.MM.dd HH:mm:SS").format(what));
    }
}
