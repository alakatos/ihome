package hu.lakati.ihome.impl.thermo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import hu.lakati.ihome.common.Event;
import hu.lakati.ihome.common.event.DataEvent;
import hu.lakati.ihome.impl.ValueConverter;
import hu.lakati.ihome.model.Gadget;
import hu.lakati.ihome.model.State;
import hu.lakati.ihome.model.StateChangedEvent;
import hu.lakati.ihome.util.MovingAverage;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Thermometer implements Gadget {

    @JsonProperty
    @Getter
    private final String name;
    
    @JsonProperty @NonNull
    @Getter
    private final String thermoSensorId;

    @JsonProperty
    private double granularity = 0.2;

    @JsonProperty
    private final ValueConverter<Integer, Double> thermoValueConverter;
    

    @Jacksonized @Builder
    public Thermometer(String name, @NonNull String thermoSensorId, double granularity,
            ValueConverter<Integer, Double> thermoValueConverter) {
        this.name = name;
        this.thermoSensorId = thermoSensorId;
        this.granularity = granularity < 0.2 ? 0.2 : granularity;
        this.thermoValueConverter = thermoValueConverter;
    }
    
    private Temperature state = new Temperature(Temperature.MIN_TEMPERATURE);
    private MovingAverage tempAvg = new MovingAverage(10); //depending on the sampling interval of the sensor input
    private double lastMarkedTemp = Temperature.MIN_TEMPERATURE;
    
    private double fireStateChangedEvent(double sensorTemp) {
        
        double newTemp = Math.round(sensorTemp*(1/granularity))*granularity;
        State oldState = state;
        state = new Temperature(newTemp);
        StateChangedEvent stateChangedEvent = StateChangedEvent.builder()
        .createDate(new Date())
        .oldState(oldState)
        .newState(state)
        .sourceId(name) //TODO add structured name
        .build();
        //TODO pass StateChangedEvent to StateChangeEventBroker

        return newTemp;
    }
    @Override
    public boolean handleEvent(Event event) {
        if (event instanceof DataEvent) {
            if (thermoSensorId.equals(event.getSourceId())) {
                DataEvent dataEvent = (DataEvent) event;
                double sensorTemp = thermoValueConverter.convert(dataEvent.getValue());
                tempAvg.add(sensorTemp);
                if (lastMarkedTemp == Temperature.MIN_TEMPERATURE) {
                    fireStateChangedEvent(Math.round(sensorTemp*(1/granularity))*granularity); //set to closest granularity value
                    //TODO fire state changed event (initial temp)
                } else {
                    if (Math.abs(tempAvg.getAvg() - lastMarkedTemp) > granularity) {
                        lastMarkedTemp = Math.round(sensorTemp*(1/granularity))*granularity;
                    }
                }

                
                state.setTempCelsius(0);
                

            } else {
                log.warn("Misrouted event: {}", event);
            }
        } else {
            log.debug("Non-data event: {}", event);
        }
        return false;
    }

    @JsonIgnore
    @Override
    public State getState() {
        return state;
    }

}
