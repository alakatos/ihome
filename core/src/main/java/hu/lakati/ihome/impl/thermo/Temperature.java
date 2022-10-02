package hu.lakati.ihome.impl.thermo;

import hu.lakati.ihome.model.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized @Builder
@Getter
@AllArgsConstructor
public class Temperature implements State {
    
    public static double MIN_TEMPERATURE = -273.15;
    private double tempCelsius = MIN_TEMPERATURE;
    
    public void setTempCelsius(double tempCelsius) {
        this.tempCelsius = tempCelsius;
    }
}
