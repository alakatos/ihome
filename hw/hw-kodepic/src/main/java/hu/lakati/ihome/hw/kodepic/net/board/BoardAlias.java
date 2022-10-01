package hu.lakati.ihome.hw.kodepic.net.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@Getter
@Jacksonized @Builder
public class BoardAlias {
    private final String alias;
    private final BoardType boardType;
}
