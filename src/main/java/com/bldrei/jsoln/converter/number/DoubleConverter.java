package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

public final class DoubleConverter extends NumberConverter<Double> {

  @Override
  public Double stringToObject(@NonNull String value) {
    return Double.valueOf(value);
  }

  protected String stringify(@NonNull Double flatValue) {
    return Double.toString(flatValue);
  }
}
