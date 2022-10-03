package hu.lakati.ihome.hw.kodepic;

import hu.lakati.ihome.common.Device;
import hu.lakati.ihome.hw.kodepic.device.board.BoardType;

public interface Board extends Runnable, Device {
    String getName();
    BoardType getBoardType();
    
}
