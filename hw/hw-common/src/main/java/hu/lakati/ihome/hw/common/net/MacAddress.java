package hu.lakati.ihome.hw.common.net;

import java.util.StringTokenizer;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MacAddress {

  private static char[] hexChars = "0123456789ABCDEF".toCharArray();

  public static final int MAC_ADDRESS_SIZE_IN_BYTES = 6;
  private byte[] addressBytes = new byte[MAC_ADDRESS_SIZE_IN_BYTES];

  public MacAddress(byte[] macAddressBytes) {
    this(macAddressBytes, 0);
  }

  public MacAddress(byte[] macAddressBytes, int offs) {
    if (macAddressBytes.length - offs < MAC_ADDRESS_SIZE_IN_BYTES) {
      throw new IllegalArgumentException("MAC addressBytes is expected to be a " + MAC_ADDRESS_SIZE_IN_BYTES
          + " length byte array");
    }
    System.arraycopy(macAddressBytes, offs, addressBytes, 0, addressBytes.length);
  }

  public MacAddress(String macAddress) {
    initFromString(macAddress, "-");
  }

  public MacAddress(String macAddress, String delimiter) {
    initFromString(macAddress, delimiter);
  }

  private void initFromString(String macAddress, String delimiter) {
    StringTokenizer stok = new StringTokenizer(macAddress, delimiter);
    if (!macAddress.toUpperCase().matches("[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}")) {
      throw new IllegalArgumentException("Illegal MAC address format: "+macAddress);
    }

    for (int i = 0; stok.hasMoreTokens(); i++) {
      String tok = stok.nextToken();
      addressBytes[i] = (byte) Integer.parseInt(tok, 16);
    }
  }

  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < addressBytes.length; i++) {
      byte b = addressBytes[i];
      appendHexString(buf, b);
      buf.append("-");
    }
    buf.setLength(buf.length() - 1);
    return buf.toString();
  }

  public byte[] getRawAddress() {
    return addressBytes;
  }

  private static void appendHexString(StringBuffer buffer, byte b) {
    int val = Byte.toUnsignedInt(b);
    int upper4Bit = val >> 4;
    int lower4Bit = val & 0x0F;
    buffer.append(hexChars[upper4Bit]).append(hexChars[lower4Bit]);
  }

}
