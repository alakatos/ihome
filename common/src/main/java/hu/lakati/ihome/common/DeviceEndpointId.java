package hu.lakati.ihome.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeviceEndpointId implements EventSource {
    private final String deviceId;
    private final String endpointId;

    @Override
    public String toString() {
        return endpointId+"@"+deviceId;
    }
}
