
package hu.lakati.ihome.hw.kodepic.config;

import hu.lakati.ihome.hw.kodepic.device.board.BoardRegistry;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized @Builder
@Getter
public class KodepicConfig {
    @Builder.Default
    private final int maxConnections = 100;
    @Builder.Default
    private final int tcpServerPort = 8888;
    @Builder.Default
    private final int localUdpListenerPort = 8889;
    private final BoardRegistry boardRegistry;
}
