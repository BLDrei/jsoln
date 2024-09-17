package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class LongConverter extends NumberConverter<Long> {
  @Override
  public Long javaify(@NotNull Number value) {
    return Long.valueOf(String.valueOf(value));
  }

  @Override
  protected Number nToLongOrBigIntOrDoubleOrBigDecimal(@NotNull Long value) {
    return value;
  }
}
