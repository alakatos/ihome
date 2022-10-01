package hu.lakati.ihome.hw.kodepic.net.board;

import java.util.HashMap;
import java.util.Map;

import hu.lakati.ihome.hw.kodepic.net.MacAddress;
import lombok.NonNull;

public class BoardRegistry {
    private final Map<MacAddress, BoardSetup> registry = new HashMap<>();

    public BoardSetup findBoardSetup(@NonNull MacAddress mac) {
        return registry.get(mac);
    }

}
