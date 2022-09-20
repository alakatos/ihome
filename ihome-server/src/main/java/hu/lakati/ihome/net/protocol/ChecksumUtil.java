package hu.lakati.ihome.net.protocol;

public class ChecksumUtil {
    /** Checksum counting */
    public static boolean isChecksumOK(byte[] data) {
        return siChecksumOK(data, data.length);
    }
    
    public static boolean siChecksumOK(byte[] data, int len) {
        byte checksumByte = countChecksum(data, len-1);
        return (checksumByte == data[len-1]);
    }
    
    public static byte countChecksum(byte[] data) {
        return countChecksum(data, data.length);
    }
    public static byte countChecksum(byte[] data, int dataLength) {
        byte b = 0;
        for (int i = 0; i < dataLength; i++) {
            b ^= data[i];
        }
        b ^= (byte)0x55;
        return b;
    }

}
