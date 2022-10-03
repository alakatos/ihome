package hu.lakati.ihome.impl;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import hu.lakati.ihome.impl.thermo.ThermoExactSensorValueConverter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ThermoExactSensorValueConverter.class, name = "exact"),
    @JsonSubTypes.Type(value = IdentityFloatConverter.class, name = "noconvert")})

public interface ValueConverter<T,R> {

    R convert(T value);
}
