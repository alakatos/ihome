package hu.lakati.ihome.net;

import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

import hu.lakati.ihome.net.protocol.ByteArrayUtil;

@EqualsAndHashCode
public class MacAddress {
  
  public static final int MAC_ADDRESS_LENGTH = 6;
  private byte[] addressBytes = new byte[MAC_ADDRESS_LENGTH];

  public MacAddress(byte[] macAddressBytes) {
    this(macAddressBytes, 0);
  }

  public MacAddress(byte[] macAddressBytes, int offs) {
    if (macAddressBytes.length - offs < MAC_ADDRESS_LENGTH) {
      throw new IllegalArgumentException("MAC addressBytes is expected to be a " + MAC_ADDRESS_LENGTH
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
      
    }
    if (stok.countTokens() != MAC_ADDRESS_LENGTH) {
      throw new IllegalArgumentException(
          "MAC addressBytes is expected in X1-X2-X3-X4-X5-X6 format wher Xn is a 2-digit hexadecimal number! ");
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
      ByteArrayUtil.appendHexString(buf, b);
      buf.append("-");
    }
    buf.setLength(buf.length() - 1);
    return buf.toString();
  }

  public byte[] getRawAddress() {
    return addressBytes;
  }


  public static MacAddress increaseAddress(MacAddress macAddress, int numLastBytes) {
    if ((numLastBytes > 6) || (numLastBytes < 1)) {
      throw new IllegalArgumentException("numLastBytes must be between 1 and 6!");
    }
    byte[] bytes = macAddress.getRawAddress();
    bytes = Arrays.copyOf(bytes, bytes.length);
    byte[] tmpBytes = Arrays.copyOfRange(bytes, MacAddress.MAC_ADDRESS_LENGTH - numLastBytes,
        MacAddress.MAC_ADDRESS_LENGTH);
    byte[] tmpMAXB = new byte[numLastBytes];
    Arrays.fill(tmpMAXB, (byte) 0xff);
    BigInteger bigInteger = new BigInteger(1, tmpBytes);
    BigInteger maxBigInteger = new BigInteger(1, tmpMAXB);
    bigInteger = bigInteger.add(BigInteger.valueOf(1));
    if (bigInteger.compareTo(maxBigInteger) > 0) {
      throw new IllegalStateException(bigInteger.toString());
    }
    byte[] newBytes = bigInteger.toByteArray();
    int j = newBytes.length - 1;
    for (int i = MacAddress.MAC_ADDRESS_LENGTH - 1; (i > MacAddress.MAC_ADDRESS_LENGTH - numLastBytes - 1) && (j >= 0); i--, j--) {
      bytes[i] = newBytes[j];
    }
    return new MacAddress(bytes);
  }

}
