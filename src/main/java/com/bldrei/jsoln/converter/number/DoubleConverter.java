package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class DoubleConverter extends NumberConverter<Double> {
  @Override
  public Double javaify(@NotNull Number value) {
    return Double.valueOf(String.valueOf(value));
  }

  @Override
  protected Number nToLongOrBigIntOrDoubleOrBigDecimal(@NotNull Double value) {
    return value;
  }
}
