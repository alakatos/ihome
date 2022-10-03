package hu.lakati.ihome.model;

import static java.util.Arrays.asList;

import org.junit.Test;

import hu.lakati.ihome.impl.thermo.ThermoExactSensorValueConverter;
import hu.lakati.ihome.impl.thermo.Thermometer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

public class AreaTest {
    @Test
    public void testGetAreas() throws JsonProcessingException {
        
        Area area = Area.builder()
        .name("Ház")
        .areas(
            asList(
                new Area("Nappali",
                    asList(
                        new Thermometer("Hőmérő", "AD_1@myRelay", 0.5, ThermoExactSensorValueConverter.builder().build())
                    )
                , null)
            )
        ).build();
        
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));
        String output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(area);
        System.out.println(output);

        Area a2 = mapper.readValue(output, Area.class);
        System.out.println(a2);

    }
}
