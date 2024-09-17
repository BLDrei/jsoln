package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class ShortConverter extends NumberConverter<Short> {

  @Override
  public Short javaify(@NotNull Number value) {
    return Short.valueOf(String.valueOf(value));
  }

  @Override
  protected Number nToLongOrBigIntOrDoubleOrBigDecimal(@NotNull Short value) {
    return (long) value;
  }
}
