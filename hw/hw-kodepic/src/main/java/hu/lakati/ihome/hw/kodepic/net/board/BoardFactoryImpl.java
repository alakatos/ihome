package hu.lakati.ihome.hw.kodepic.net.board;

import hu.lakati.ihome.common.EventBroker;
import hu.lakati.ihome.hw.kodepic.net.Board;
import hu.lakati.ihome.hw.kodepic.net.BoardFactory;
import hu.lakati.ihome.hw.kodepic.net.protocol.PacketProtocol;

public class BoardFactoryImpl implements BoardFactory {
    public Board createBoard(PacketProtocol protocol, EventBroker eventBroker) {
        return new BoardImpl(protocol, eventBroker);
    }
}
