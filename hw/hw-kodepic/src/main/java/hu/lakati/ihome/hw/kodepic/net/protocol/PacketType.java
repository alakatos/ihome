package hu.lakati.ihome.hw.kodepic.net.protocol;

public enum PacketType {
    RESETPIC,
    STARTUP,
    ALIVE,
    ERROR,
    DATA,
    CONFIG,
    CONTROL,
    PANEL_STATUS,
    PORT_STATUS;

    static PacketType fromData(byte[] data) throws EHomeProtocolException {
        if (data.length < 1) {
            throw new EHomeProtocolException("Data array has to contain at least 1 byte");
        }
        int ordinal =  Byte.toUnsignedInt(data[0])-1;
        PacketType[] values = values();
        if (ordinal < 0 || ordinal > values.length-1) {
            throw new EHomeProtocolException("Invalid ordinal of packet type: "+ordinal);
        }
        return values[ordinal];
    }
}