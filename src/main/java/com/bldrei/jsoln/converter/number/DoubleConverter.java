package com.bldrei.jsoln.converter.number;

public final class DoubleConverter extends NumberConverter<Double> {

  public DoubleConverter() {
    super(Double.class);
  }

  @Override
  public Double convert(String value) {
    return Double.valueOf(value);
  }
}
