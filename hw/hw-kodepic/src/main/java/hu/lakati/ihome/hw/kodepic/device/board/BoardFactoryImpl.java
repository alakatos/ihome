package hu.lakati.ihome.hw.kodepic.device.board;

import hu.lakati.ihome.common.EventBroker;
import hu.lakati.ihome.hw.kodepic.Board;
import hu.lakati.ihome.hw.kodepic.BoardFactory;
import hu.lakati.ihome.hw.kodepic.net.protocol.PacketProtocol;

public class BoardFactoryImpl implements BoardFactory {

    @Override
    public Board createBoard(PacketProtocol protocol, EventBroker eventBroker, BoardAlias alias) {
        return new BoardImpl(protocol, eventBroker, alias);
    }
}
