package hu.lakati.ihome.hw.kodepic.net.protocol;

import java.util.ArrayList;
import java.util.List;

import hu.lakati.ihome.hw.kodepic.net.MacAddress;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StartupPacket extends Packet {

  enum CauseOfReset {
    BROWN_OUT, // 0x01), //LSB
    POWER_ON, // 0x02),
    POWER_DOWN, // 0x04),
    WATCHDOG_TIMEOUT, // 0x08),
    RESET_INSTRUCTION, // 0x10),
    CONFIGURATION_MISMATCH, // 0x20),
    STACK_UNDERRUN, // 0x40),
    STACK_FULL; // 0x80); //MSB

    public byte getCause() {
      return (byte) (1 << (this.ordinal() - 1));
    }

    public static List<CauseOfReset> fromCause(byte cause) {
      List<CauseOfReset> lst = new ArrayList<>();
      int causeAsInt = Byte.toUnsignedInt(cause);

      for (int i = 1, j = 0; i <= 256; i = i * 2, j++) {
        if ((causeAsInt & i) != 0) {
          lst.add(values()[j]);
        }
      }
      return lst;
    }

  }

  private List<CauseOfReset> causesOfReset;
  private MacAddress macAddress;
  private String boardType;
  private String boardFirmware;

  protected StartupPacket(byte[] data) throws EHomeProtocolException {
    super(PacketType.STARTUP, data);
  }

  @Override
  protected int parseData(byte[] data) throws EHomeProtocolException {

    int offset = super.parseData(data);

    causesOfReset = CauseOfReset.fromCause(data[offset]);
    offset++;
    offset = readMacAddress(data, offset);
    offset = readBoardTypeInfo(data, offset);
    return offset;
  }

  private int readBoardTypeInfo(byte[] data, int offset) {
    int nameLen = data.length - offset;
    boardType = new String(data, offset, nameLen);
    int pos = boardType.indexOf(':');
    if (pos > -1) {
      boardFirmware = boardType.substring(pos + 1);
      boardType = boardType.substring(0, pos);
    }
    return data.length;
  }

  private int readMacAddress(byte[] data, int offset) throws EHomeProtocolException {
    try {
      macAddress = new MacAddress(data, offset);
    } catch (IllegalArgumentException e) {
      throw new EHomeProtocolException("Can't parse MAC address - " + e);
    }
    return  MacAddress.MAC_ADDRESS_LENGTH;
  }
}
