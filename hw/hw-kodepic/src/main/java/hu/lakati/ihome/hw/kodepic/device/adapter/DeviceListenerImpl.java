package hu.lakati.ihome.hw.kodepic.device.adapter;

import hu.lakati.ihome.common.Device;
import hu.lakati.ihome.common.DeviceListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeviceListenerImpl implements DeviceListener {

  @Override
  public void deviceAdded(Device device) {
    log.info("Device added {}", device);
  }

  @Override
  public void deviceRemoved(Device device) {
    log.info("Device removed {}", device);
  }
  
}
