package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class IntegerConverter extends NumberConverter<Integer> {
  @Override
  public Integer javaify(@NotNull Number value) {
    return Integer.valueOf(String.valueOf(value));
  }

  @Override
  protected Number nToLongOrBigIntOrDoubleOrBigDecimal(@NotNull Integer value) {
    return (long) value;
  }
}
