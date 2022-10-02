package hu.lakati.ihome.hw.kodepic.net.protocol;

import com.fasterxml.jackson.annotation.JsonValue;

import hu.lakati.ihome.hw.kodepic.net.IOMode;
import lombok.Getter;

/**
 * 
 * @author lakatosa
 */
@Getter
public enum PortType {
    BINARY_HI(1, 0),
    AD(2, 3),
    BINARY_LO(1, 0),
    INFRA(2, 0),
    DIMMER(1, 0),

    RELAY(1, 0),
    INFRA_OUT(2, 0),
    SERIAL(-1, 0),

    RFID(10, 0), // 10 bytes for GP_60_A_I
    DA(2, 0),

    LAMPPRG(4, 0),
    DIMMSTATUS(1, 0),
    RF(1, 0);

    private final int valueLength; // value length in bytes
    private final int configLength;

    private PortType(int valueLength, int configLength) {
        this.valueLength = valueLength;
        this.configLength = configLength;
    }

    public int getPacketId() {
        return ordinal() + 1;
    }

    public static PortType getById(final int id) {
        if (id < 1 || id > values().length) {
            throw new IllegalArgumentException("Invalid packet port type id " + id);
        }
        return values()[id-1];
    }

    public boolean isBinary() {
        return this == BINARY_HI || this == BINARY_LO || this == RELAY;
    }

    public IOMode getIOMode() {
        switch (this) {
            case AD:
            case BINARY_HI:
            case BINARY_LO:
            case DIMMSTATUS:
                return IOMode.Input;

            case DIMMER:
            case RELAY:
            case DA:
            case LAMPPRG:
                return IOMode.Output;

            default:
                throw new IllegalStateException("I/O mode of " + this + " is not registered");
        }
    }

    @JsonValue
    public String getName() {
        return name();
    }
}