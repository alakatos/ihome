package hu.lakati.ihome.impl.thermo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import org.junit.Test;

public class ThermoSensorValueConverterTest {
    @Test
    public void testConvert() {
        ThermoExactSensorValueConverter converter = new ThermoExactSensorValueConverter();
        double tempCelsius = converter.convert(2000d);
        assertThat(2000, lessThan(ThermoExactSensorValueConverter.conversionValues[25][0]));
        assertThat(2000, greaterThan(ThermoExactSensorValueConverter.conversionValues[24][0]));
        assertThat(tempCelsius, greaterThan((double)ThermoExactSensorValueConverter.conversionValues[25][1]));
        assertThat(tempCelsius, lessThan((double)ThermoExactSensorValueConverter.conversionValues[24][1]));
    }
}
