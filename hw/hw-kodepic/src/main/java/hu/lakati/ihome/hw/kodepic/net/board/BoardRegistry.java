package hu.lakati.ihome.hw.kodepic.net.board;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import hu.lakati.ihome.hw.common.net.MacAddress;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Builder @Jacksonized
public class BoardRegistry {
    @JsonProperty("boards")
    private final Map<MacAddress, BoardAlias> registry;

    public BoardAlias findBoardAlias(@NonNull MacAddress mac) {
        return registry.get(mac);
    }

}
