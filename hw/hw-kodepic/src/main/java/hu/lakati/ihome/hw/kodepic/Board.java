package hu.lakati.ihome.hw.kodepic;

import hu.lakati.ihome.common.CommandTarget;
import hu.lakati.ihome.hw.kodepic.device.board.BoardType;

public interface Board extends Runnable, CommandTarget {
    String getName();
    BoardType getBoardType();
    
}
