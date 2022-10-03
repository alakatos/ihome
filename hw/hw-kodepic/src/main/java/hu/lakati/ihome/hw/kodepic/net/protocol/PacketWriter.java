package hu.lakati.ihome.hw.kodepic.net.protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import hu.lakati.ihome.hw.common.net.MacAddress;

final class PacketWriter {
    private final long timeDiff;
    private final ByteArrayOutputStream dataBuf;

    PacketWriter(long timeDiff) {
        this.timeDiff = timeDiff;
        dataBuf = new ByteArrayOutputStream();
    }

    byte[] getData() {
        return dataBuf.toByteArray();
    }

    void writePacketType(PacketType packetType) {
        dataBuf.write(packetType.ordinal() + 1);
    }

    void writeDate(Date date) {
        int boardTime = (int) (date.getTime() - timeDiff);
        for (int i = 0; i < 4; i++) {
            dataBuf.write((byte) ((boardTime >> (i * 8)) & 0xff));
        }
    }

    void writeIntValue(int value, int length) {
        switch (length) {
            case 1:
            case 2:
            case 4:
                for (int i = 0; i < length; i++) {
                    dataBuf.write((byte) ((value >> (i * 8)) & 0xff));
                }
                break;
            default:
                throw new IllegalArgumentException("Value length can be one of (1,2,4), but it was " + length);
        }
    }

    void writeMacAddress(MacAddress macAddress) {
        try {
            dataBuf.write(macAddress.getRawAddress());
        } catch (IOException e) {
            //cannot happen
        }
    }

    void write(String s) {
        try {
            dataBuf.write(s.getBytes());
        } catch (IOException e) {
            //cannot happen
        }
    }

}
