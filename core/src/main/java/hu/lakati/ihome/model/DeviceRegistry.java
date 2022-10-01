package hu.lakati.ihome.model;

import hu.lakati.ihome.common.DeviceEndpointId;

public interface DeviceRegistry {
    Device lookup(DeviceEndpointId deviceEndpointId);
}
