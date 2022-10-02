package hu.lakati.ihome.hw.kodepic.net.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

public class PacketProtocol {

  private static final int PACKET_START_BYTE = 0xAA;

  // length of the packet length header (2 bytes integer + 1 byte checksum)
  static final int TCP_PACKET_LENGTH_HEADER_LENGTH = 3;

  // the packet ID + 4 bytes timestamp
  static final int TCP_PACKET_HEADER_LENGTH = 5;
  static final int TCP_PACKET_MIN_LENGTH = TCP_PACKET_HEADER_LENGTH;
  static final int TCP_PACKET_MAX_LENGTH = 8192;

  private final Socket socket;
  private Long timeDiff;

  private OutputStream out;

  private InputStream input;

  public PacketProtocol(Socket socket) throws IOException {
    this.socket = socket;
    input = socket.getInputStream();
    out = socket.getOutputStream();
  }

  public void close() throws IOException {
    if (!socket.isClosed()) {
      writePacket(new ResetPICPacket());
      socket.close();
    }
  }
  

  public Packet readPacket() throws EHomeProtocolException, IOException {
    int dataLength;
    do {
      dataLength = tryReadDataLength();
    } while (dataLength == -1);

    if (dataLength < TCP_PACKET_HEADER_LENGTH) {
      throw new EHomeProtocolException("Packet length too short: " + dataLength);
    }

    return PacketFactory.createPacket(readData(dataLength));
  }

  private void findPossiblePacketStart() throws IOException, EHomeProtocolException {
    int ch;
    do {
      ch = input.read();
      if (ch == -1) {
        throw new EHomeProtocolException("Input stream closed unexpectedly");
      }
    } while (ch != PACKET_START_BYTE);
  }

  private int tryReadDataLength() throws IOException, EHomeProtocolException {
    findPossiblePacketStart();
    input.mark(TCP_PACKET_LENGTH_HEADER_LENGTH); // marking the position after AA. If the packet length header is
                                                 // invalid, the stream will to be reset to this position
    byte[] header = readData(TCP_PACKET_HEADER_LENGTH);
    if (ChecksumUtil.isChecksumOK(header)) { // header checksum
      int dataLength = ByteArrayUtil.parse16bitUint(header, 0);
      if (dataLength >= TCP_PACKET_HEADER_LENGTH && dataLength <= TCP_PACKET_MAX_LENGTH) {
        return dataLength;
      }
    }

    input.reset();
    return -1;
  }

  /**
   * Reads the data taking care of interrupted read events. These event can be
   * caused by
   * outgoing TCP/IP ALIVE ACK messages
   * 
   * @throws EHomeProtocolException
   */
  private byte[] readData(int dataLength) throws IOException, EHomeProtocolException {
    byte dataBuf[] = new byte[dataLength];
    // Arrays.fill(dataBuf, (byte) 0x88);
    int bytesRead = input.read(dataBuf);
    if (bytesRead < dataBuf.length) {
      readRemainingData(dataBuf, bytesRead);
    }
    return dataBuf;
  }

  private void readRemainingData(byte[] data, int bytesRead)
      throws IOException, EHomeProtocolException {
    // NOTE!
    // BufferedInputStream.read(byte[]) can return with an int smaller than the size
    // of the byte array,
    // even if the BufferedInputStream.read() still CAN read it byte by byte
    // This behaviour does not correspond with that of written in the spec
    for (int i = bytesRead; i < data.length; i++) {
      int ch = input.read();
      if (ch == -1) {
        throw new EHomeProtocolException(
            "EOF detected when reading " + i + " bytes instead of " + data.length + " bytes" + data);
      }
      data[i] = (byte) ch;
    }
  }

  public void writePacket(Packet packet) throws IOException {
    byte[] packData = packet.toByteArray();
    byte[] header = new byte[3];
    ByteArrayUtil.intToByteArray(packData.length, header, 0);
    header[2] = ChecksumUtil.countChecksum(header, 2);
    byte[] bytesToSend = ByteArrayUtil.concatByteArrays(new byte[] { (byte) 0xAA }, header, packData);
    try {
      out.write(bytesToSend);
      out.flush();
    } catch (IOException e) {
    }
  }

  public Date currentPacketTime() {
    if (timeDiff == null) {
      return new Date();
    } else {
      return new Date(System.currentTimeMillis()+timeDiff);
    }
  }
}
