package hu.lakati.ihome.hw.kodepic.net.protocol;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayUtil {

  private static char[] hexChars = "0123456789ABCDEF".toCharArray();

  public static boolean startsWith(byte[] barr, String prefix) {
    return startsWith(barr, prefix, 0);
  }

  public static boolean startsWith(byte[] barr, String prefix, int offs) {
    byte[] prefixBArr = prefix.getBytes();
    if (prefixBArr.length > barr.length - offs) {
      return false;
    }
    for (int i = 0; i < prefixBArr.length; i++) {
      if (prefixBArr[i] != barr[i + offs]) {
        return false;
      }
    }
    return true;
  }

  public static byte[] concatByteArrays(byte[]... byteArrays) {
    int len = 0;
    for (byte[] bArr : byteArrays) {
      len += bArr == null ? 0 : bArr.length;
    }
    byte[] retVal = new byte[len];
    int pos = 0;
    for (byte[] bArr : byteArrays) {
      if (bArr != null) {
        for (int i = 0; i < bArr.length; i++) {
          retVal[pos + i] = bArr[i];
        }
        pos += bArr.length;
      }
    }
    return retVal;
  }

  public static String dumpData(byte[] data) {
    return dumpData(data, data == null ? 0 : data.length);
  }

  public static String dumpData(byte[] data, int len) {
    return dumpData(data, len, 0);
  }

  public static String dumpData(byte[] data, int len, int offs) {
    if (data == null || data.length < 1) {
      return "[]";
    }
    StringBuffer buf = new StringBuffer("[");
    appendHexString(buf, data[offs]);
    for (int i = 1; i < len; i++) {
      buf.append(",");
      appendHexString(buf, data[offs + i]);
    }
    buf.append("]");
    return buf.toString();
  }

  public static int parse4UInt(byte[] data, int offset) {
    int value = 0;

    for (int i = 0; i < 4; i++) { //first byte was the package type byte
      //TODO check if to keep negative value of uppermost (4th) byte
      value += Byte.toUnsignedInt(data[i + offset]) << (i * 8);
    }
    return value;
  }

  /** Parses an unsigned int from the given 2 length byte array */
  public static int parseInt(byte[] data, int offset) {
    return Byte.toUnsignedInt(data[offset]) + Byte.toUnsignedInt(data[offset+1]) << 8;
  }

  /** Parses a signed or unsigned integer given by <code>length</code> of bytes 
   * in the given byte array from the specified offset. 
   * The Most Valuable Byte is the rightmost byte, in which the sign bit is the leftmost bit. 
   * @exception IllegalArgumentException if the given length is longer than the byte size of the Java int type */
  public static int parseInt(byte[] data, int offset, int length, boolean signed) {
    if (length > Integer.SIZE / 8) {
      throw new IllegalArgumentException("Maximum number of bytes storing Java integers is " + (Integer.SIZE / 8)
          + ". " + length + " was provided.");
    }
    int result = 0;
    boolean negative = false;
    int xorValue = 0;
    for (int i = length - 1; i >= 0; i--) {
      byte b = data[offset + i];
      if (i == length - 1) {
        if ((b & 0x80) != 0) {
          negative = true;
        }
      }
      int iB = Byte.toUnsignedInt(b);
      result <<= 8;
      result += iB;
      xorValue <<= 8;
      xorValue += 0xFF;
    }
    if (negative) {
      result = -1 * (result ^ xorValue) - 1;
    }
    return result;
  }

  /** Returns the 2 bytes long representation of the unsigned value */
  public static byte[] intToByteArray(int value) {
    byte[] barr = new byte[2];
    intToByteArray(value, barr, 0);
    return barr;
  }

  public static void intToByteArray(int value, byte[] data, int offset) {
    value %= 0x10000;
    if (value < 0) {
      value += 65536;
    }
    data[0] = (byte) (value & 0xff);//(byte)(value % 256);
    data[1] = (byte) (value >> 8 & 0xff);
  }

  public static String readStringFromInputStream(InputStream is) throws IOException {
    byte[] buf = new byte[1024];
    int len;
    StringBuffer strBuf = new StringBuffer();
    while ((len = is.read(buf)) == buf.length) {
      strBuf.append(new String(buf));
    }
    if (len > 0) {
      strBuf.append(new String(buf, 0, len));
    }
    return strBuf.toString();
  }

  public static String toHexString(byte[] bArr) {
    if (bArr == null || bArr.length == 0) {
      return null;
    }
    StringBuffer sBuf = new StringBuffer();
    for (int i = 0; i < bArr.length; i++) {
      appendHexString(sBuf, bArr[i]);
    }
    return sBuf.toString();
  }

  public static void appendHexString(StringBuffer buffer, byte b) {
    int val = Byte.toUnsignedInt(b);
    int upper4Bit = val >> 4;
    int lower4Bit = val & 0x0F;
    buffer.append(hexChars[upper4Bit]).append(hexChars[lower4Bit]);
  }

  public static String toBinaryString(byte b) {
    StringBuffer buf = new StringBuffer("00000000");
    for (byte i = 0; i < 8; i++) {
      if ((b & (1 << i)) > 0) {
        buf.setCharAt(7 - i, '1');
      }
    }
    return buf.toString();
  }

  public static byte[] parseHexString(String hexString) throws NumberFormatException {
    hexString = hexString.trim();
    int len = hexString.length();
    if (len > 1 && len % 2 != 0) {
      throw new NumberFormatException("Hex string '" + hexString
          + "' should contain non-zero even numbers of hexadecimal characters!");
    }
    if (len == 1) {
      return new byte[] { (byte) Integer.parseInt(hexString, 16) };
    } else {
      byte[] barr = new byte[len / 2];
      for (int i = 0; i < len / 2; i++) {
        barr[i] = (byte) Integer.parseInt("" + hexString.charAt(i * 2) + hexString.charAt(i * 2 + 1), 16);
      }
      return barr;
    }
  }

  public static byte[] subArray(byte[] bArr, int offset) {
    byte[] retArr = null;
    if (bArr != null && bArr.length > offset) {
      retArr = new byte[bArr.length - offset];
      System.arraycopy(bArr, offset, retArr, 0, retArr.length);
    }
    return retArr;
  }

  public static String dumpNiceData(byte[] data) {
    if (data == null || data.length < 1) {
      return "[]";
    }
    StringBuffer buf = new StringBuffer("[");
    for (int i = 0; i < data.length; i++) {
      byte b = data[i];
      char ch = (char)b;
      if (Character.isLetter(ch)) {
        buf.append("'"+ch+"'");
      } else {
        appendHexString(buf, b);
      }
      if (i<data.length-1) {
        buf.append(",");
      }
    }
    buf.append("]");
    return buf.toString();
  }

  private ByteArrayUtil() {
  }
}
