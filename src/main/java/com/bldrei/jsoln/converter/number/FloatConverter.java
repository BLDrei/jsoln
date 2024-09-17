package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class FloatConverter extends NumberConverter<Float> {
  @Override
  public Float javaify(@NotNull Number value) {
    return Float.valueOf(String.valueOf(value));
  }

  @Override
  protected Number nToLongOrBigIntOrDoubleOrBigDecimal(@NotNull Float value) {
    return (double) value;
  }
}
