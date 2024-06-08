package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

public final class DoubleConverter extends NumberConverter<Double> {

  @Override
  public Double convert(@NonNull String value) {
    return Double.valueOf(value);
  }

  @Override
  protected String stringify(@NonNull Double flatValue) {
    return Double.toString(flatValue);
  }
}
