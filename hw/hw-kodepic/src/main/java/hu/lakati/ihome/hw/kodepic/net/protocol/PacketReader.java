package hu.lakati.ihome.hw.kodepic.net.protocol;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import hu.lakati.ihome.hw.common.net.MacAddress;

class PacketReader {
    private static final int DATA_LENGTH_PACKET_DATE = 4;
    private final InputStream dataStream;
    private long timeDiff;

    PacketReader(byte[] data, long timeDiff) {
        this.dataStream = new ByteArrayInputStream(data);
        this.timeDiff = timeDiff;
    }

    public void setTimeDiff(long timeDiff) {
        this.timeDiff = timeDiff;
    }

    PacketType readPacketType() throws EHomeProtocolException {
            int ordinal = Byte.toUnsignedInt(readData()) - 1;
            PacketType[] values = PacketType.values();
            if (ordinal < 0 || ordinal > values.length - 1) {
                throw new EHomeProtocolException("Invalid packet type referenc: " + ordinal);
            }
            return values[ordinal];
    }

    Date readDate() throws EHomeProtocolException {
        long longValue = readIntValue(DATA_LENGTH_PACKET_DATE) & 0x00000000ffffffffL;
        return new Date(timeDiff+longValue);
    }

    int readIntValue(int valueLength) throws EHomeProtocolException {
        switch (valueLength) {
            case 1:
                return Byte.toUnsignedInt(readData());
            case 2:
                return ByteArrayUtil.parse16bitUint(readData(2),0);
            default:
                return ByteArrayUtil.parseInt(readData(valueLength), 0, valueLength);
        }
    }

    MacAddress readMacAddress() throws EHomeProtocolException {
        return new MacAddress(readData(MacAddress.MAC_ADDRESS_SIZE_IN_BYTES));
    }

    byte readData() throws EHomeProtocolException {
        try {
            int ch = dataStream.read();
            if (ch < 0) {
                throw new EHomeProtocolException("End of data");
            }
            return (byte)ch;
        } catch (IOException e) {
            throw new EHomeProtocolException("End of data");
        }
    }

    String readRemainingAsString() throws EHomeProtocolException {
        return new String(readData(8192, false));
    }

    byte[] readData(int valueLength) throws EHomeProtocolException {
        return readData(valueLength, true);
    }

    private byte[] readData(int valueLength, boolean failOnNoMoreData) throws EHomeProtocolException {
        byte[] buf = new byte[valueLength];
        try {
            int readLen = dataStream.read(buf);
            if (readLen < valueLength && failOnNoMoreData) {
                throw new EHomeProtocolException("End of data");
            }
            return buf;
        } catch (IOException e) {
            throw new EHomeProtocolException("End of data");
        }
    }

}
