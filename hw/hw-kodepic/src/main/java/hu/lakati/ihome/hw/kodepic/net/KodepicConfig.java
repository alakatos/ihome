
package hu.lakati.ihome.hw.kodepic.net;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KodepicConfig {
    @Builder.Default
    private final int maxConnections = 100;
    @Builder.Default
    private final int tcpServerPort = 8888;
    @Builder.Default
    private final int localUdpListenerPort = 8889;
}
