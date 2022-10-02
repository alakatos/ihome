package hu.lakati.ihome.hw.kodepic.net.protocol;

import java.util.ArrayList;
import java.util.List;

import hu.lakati.ihome.hw.common.net.MacAddress;
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

    public static List<CauseOfReset> fromCause(int cause) {
      List<CauseOfReset> lst = new ArrayList<>();

      for (int i = 1, j = 0; i <= 256; i = i * 2, j++) {
        if ((cause & i) != 0) {
          lst.add(values()[j]);
        }
      }
      return lst;
    }

  }

  private final List<CauseOfReset> causesOfReset;
  private final MacAddress macAddress;
  private final String boardType;
  private final String boardFirmware;

  protected StartupPacket(PacketReader packetReader) throws EHomeProtocolException {
    super(PacketType.STARTUP, packetReader);

    causesOfReset = CauseOfReset.fromCause(packetReader.parseUintValue(1));
    macAddress = packetReader.parseMacAddress();

    String boardType = packetReader.readRemainingAsString();

    int pos = boardType.indexOf(':');
    if (pos > -1) {
      this.boardFirmware = boardType.substring(pos + 1);
      this.boardType = boardType.substring(0, pos);
    } else {
      this.boardType = boardType;
      this.boardFirmware = "UNKNOWN";
    }
  }

}
