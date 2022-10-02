package hu.lakati.ihome.impl.thermo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import org.junit.Test;

public class ThermoSensorValueConverterTest {
    @Test
    public void testConvert() {
        ThermoSensorValueConverter converter = new ThermoSensorValueConverter();
        double tempCelsius = converter.convert(2000);
        assertThat(2000, lessThan(ThermoSensorValueConverter.conversionValues[25][0]));
        assertThat(2000, greaterThan(ThermoSensorValueConverter.conversionValues[24][0]));
        assertThat(tempCelsius, greaterThan((double)ThermoSensorValueConverter.conversionValues[25][1]));
        assertThat(tempCelsius, lessThan((double)ThermoSensorValueConverter.conversionValues[24][1]));
    }
}
