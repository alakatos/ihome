package hu.lakati.ihome.hw.kodepic.net;

import hu.lakati.ihome.common.CommandTarget;
import hu.lakati.ihome.hw.kodepic.net.board.BoardType;

public interface Board extends Runnable, CommandTarget {
    String getName();
    BoardType getBoardType();
    
}
