package hu.lakati.ihome.hw.kodepic.device.adapter;

import org.w3c.dom.events.Event;

import hu.lakati.ihome.hw.common.DeviceType;
import hu.lakati.ihome.hw.kodepic.net.protocol.DataPacket;
import hu.lakati.ihome.hw.kodepic.net.protocol.Packet;

public class SwitchAdapter implements KodePicInputAdapter {

    private final DeviceType switchType;

    public SwitchAdapter(DeviceType deviceType) {
        this.switchType = null;
    }

    @Override
    public Event convertToEvent(Packet lowLevelData) {
        if (lowLevelData instanceof DataPacket) {
            DataPacket dataPacket = (DataPacket) lowLevelData;
            
        }
        return null;
    }
    
}
