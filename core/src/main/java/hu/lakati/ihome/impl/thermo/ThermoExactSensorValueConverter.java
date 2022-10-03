package hu.lakati.ihome.impl.thermo;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hu.lakati.ihome.impl.ValueConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized @Builder
@JsonIgnoreProperties
public class ThermoExactSensorValueConverter implements ValueConverter<Double, Double> {

    @AllArgsConstructor
    @Getter
    private static class TempConversionEntry implements Comparable<TempConversionEntry> {
        double sensorAdValue;
        double temperatureCelsius;

        @Override
        public int compareTo(TempConversionEntry o) {
            return Double.compare(sensorAdValue, o.sensorAdValue);
        }
    }

    static int[][] conversionValues = {
            { 74, 150 },
            { 83, 145 },
            { 93, 140 },
            { 105, 135 },
            { 119, 130 },
            { 134, 125 },
            { 153, 120 },
            { 174, 115 },
            { 198, 110 },
            { 227, 105 },
            { 260, 100 },
            { 298, 95 },
            { 343, 90 },
            { 396, 85 },
            { 457, 80 },
            { 528, 75 },
            { 610, 70 },
            { 705, 65 },
            { 815, 60 },
            { 941, 55 },
            { 1084, 50 },
            { 1245, 45 },
            { 1423, 40 },
            { 1618, 35 },
            { 1827, 30 },
            { 2048, 25 },
            { 2275, 20 },
            { 2503, 15 },
            { 2726, 10 },
            { 2938, 5 },
            { 3135, 0 },
            { 3313, -5 },
            { 3468, -10 },
            { 3601, -15 },
            { 3713, -20 },
            { 3803, -25 },
            { 3876, -30 },
            { 3933, -35 },
            { 3977, -40 },
            { 4010, -45 },
            { 4035, -50 },
            { 4053, -55 },
            { 4066, -60 },
            { 4076, -65 },
            { 4082, -70 },
            { 4087, -75 },
            { 4090, -80 },

    };

    private static TempConversionEntry[] sortedConversionEntries;
    static {
        sortedConversionEntries = Stream.of(conversionValues)
                .map(cv -> new TempConversionEntry(cv[0], (double) cv[1])).sorted().collect(Collectors.toList())
                .toArray(new TempConversionEntry[0]);
    }

    @Override
    public Double convert(Double sensorValue) {
        int idx = Arrays.binarySearch(sortedConversionEntries, new TempConversionEntry(sensorValue, 0));
        if (idx >= 0) {
            return sortedConversionEntries[idx].temperatureCelsius;
        }
        idx = idx *-1 -1;
        if (idx <= 0) {
            return (double) sortedConversionEntries[0].temperatureCelsius;
        }
        if (idx >= sortedConversionEntries.length) {
            return (double) sortedConversionEntries[sortedConversionEntries.length - 1].temperatureCelsius;
        }

        TempConversionEntry t1 = sortedConversionEntries[idx-1];
        TempConversionEntry t2 = sortedConversionEntries[idx];

        double rangePercentage = (double)(sensorValue - t1.sensorAdValue) / (t2.sensorAdValue - t1.sensorAdValue);
        
        return t2.temperatureCelsius - (t2.temperatureCelsius - t1.temperatureCelsius) * rangePercentage;
    }

}
