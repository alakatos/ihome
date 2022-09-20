package hu.lakati.ihome.reg;

import java.util.HashMap;
import java.util.Map;

import hu.lakati.ihome.net.MacAddress;
import lombok.NonNull;

public class BoardRegistry {
    private final Map<MacAddress, BoardSetup> registry = new HashMap<>();

    public BoardSetup findBoardSetup(@NonNull MacAddress mac) {
        return registry.get(mac);
    }

}
