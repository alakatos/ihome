package hu.lakati.ihome.impl;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import hu.lakati.ihome.impl.thermo.ThermoSensorValueConverter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ThermoSensorValueConverter.class, name = "exact")})

public interface ValueConverter<T,R> {

    R convert(T value);
}
