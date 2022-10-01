package hu.lakati.ihome.model;

import java.util.List;

public interface Device {
    List<DeviceEndpoint> getEndpoints();
}
