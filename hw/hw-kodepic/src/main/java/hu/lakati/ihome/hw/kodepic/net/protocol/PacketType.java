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
}