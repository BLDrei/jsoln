package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

public final class DoubleConverter extends NumberConverter<Double> {

  public DoubleConverter() {
    super(Double.class);
  }

  @Override
  public Double convert(String value) {
    return Double.valueOf(value);
  }

  @Override
  protected String stringifyT(@NonNull Double flatValue) {
    return Double.toString(flatValue);
  }
}
