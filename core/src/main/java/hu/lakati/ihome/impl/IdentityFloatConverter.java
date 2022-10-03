package hu.lakati.ihome.impl;

public class IdentityFloatConverter implements ValueConverter<Double,Double> {

  @Override
  public Double convert(Double value) {
    return value;
  }

}
