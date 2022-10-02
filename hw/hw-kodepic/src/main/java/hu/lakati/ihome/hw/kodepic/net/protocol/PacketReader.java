package hu.lakati.ihome.hw.kodepic.net.protocol;

import java.io.Closeable;
import java.util.Date;

import hu.lakati.ihome.hw.common.net.MacAddress;
import lombok.Getter;

class PacketReader {
    private static final int DATA_LENGTH_PACKET_TYPE = 1;
    private static final int DATA_LENGTH_PACKET_DATE = 4;
    private static final int DATA_LENGTH_PORT_TYPE = 2;
    private final byte[] data;

    private int offset;
    @Getter
    private final PacketType packetType;

    private class ValidatingOffset implements Closeable {
        private int increment;

        ValidatingOffset(int increment) throws EHomeProtocolException {
            this.increment = increment;
            if (offset + increment > data.length) {
                throw new EHomeProtocolException(
                        "Data size " + (data.length - offset) + " is less then expected length of " + increment);
            }
        }

        @Override
        public void close() {
            offset += increment;
        }
    }

    PacketReader(byte[] data) throws EHomeProtocolException {
        this.data = data;
        packetType = parsePacketType();
    }

    private final PacketType parsePacketType() throws EHomeProtocolException {
        try (ValidatingOffset offsetValidator = new ValidatingOffset(DATA_LENGTH_PACKET_TYPE)) {
            int ordinal = Byte.toUnsignedInt(data[offset]) - 1;
            PacketType[] values = PacketType.values();
            if (ordinal < 0 || ordinal > values.length - 1) {
                throw new EHomeProtocolException("Invalid packet type referenc: " + ordinal);
            }
            return values[ordinal];
        }
    }

    Date parsePacketDate() throws EHomeProtocolException {
        try (ValidatingOffset offsetValidator = new ValidatingOffset(DATA_LENGTH_PACKET_DATE)) {

            int dateValue = parseIntValue(DATA_LENGTH_PACKET_DATE);
            long longValue = dateValue < 0 ? -1*dateValue : dateValue;
            // !!! unusable date value (4 bytes) parsing will be skipped
            // long timestamp = 0;
            // for (int i=0; i < 4; i++) {
            // long bVal = Byte.toUnsignedInt(data[i+1]); //0th byte was the packetType
            // timestamp += bVal << (i*8);
            // }
            // createDate = new Date(timestamp);
            return new Date(longValue);
        }
    }

    PortType parsePortType() throws EHomeProtocolException {
        try (ValidatingOffset offsetValidator = new ValidatingOffset(DATA_LENGTH_PORT_TYPE)) {
            int portTypeId = Byte.toUnsignedInt(data[offset]);
            return PortType.getById(portTypeId);
        } catch (IllegalArgumentException e) {
            throw new EHomeProtocolException(e.getMessage());
        }
    }

    int parseIntValue(int valueLength) throws EHomeProtocolException {
        try (ValidatingOffset offsetValidator = new ValidatingOffset(valueLength)) {
            switch (valueLength) {
                case 1:
                    return Byte.toUnsignedInt(data[offset]);
                case 2:
                    return ByteArrayUtil.parse16bitUint(data, offset);
                default:
                    return ByteArrayUtil.parseInt(data, offset, valueLength);
            }
        }
    }

    MacAddress parseMacAddress() throws EHomeProtocolException {
        try (ValidatingOffset offsetValidator = new ValidatingOffset(MacAddress.MAC_ADDRESS_LENGTH)) {
            return new MacAddress(data, offset);
        }
    }

    String readRemainingAsString() throws EHomeProtocolException {
        try (ValidatingOffset offsetValidator = new ValidatingOffset(data.length - offset)) {
            return new String(data, offset, data.length - offset);
        }
    }

}
