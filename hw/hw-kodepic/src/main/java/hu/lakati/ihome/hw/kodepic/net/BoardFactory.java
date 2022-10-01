package hu.lakati.ihome.hw.kodepic.net;

import hu.lakati.ihome.common.EventBroker;
import hu.lakati.ihome.hw.kodepic.net.board.BoardAlias;
import hu.lakati.ihome.hw.kodepic.net.protocol.PacketProtocol;

public interface BoardFactory {
    Board createBoard(PacketProtocol protocol, EventBroker eventBroker, BoardAlias alias);
}
