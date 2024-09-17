package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class ByteConverter extends NumberConverter<Byte> {
  @Override
  public Byte javaify(@NotNull Number value) {
    return Byte.valueOf(String.valueOf(value));
  }

  @Override
  protected Number nToLongOrBigIntOrDoubleOrBigDecimal(@NotNull Byte value) {
    return (long) value;
  }
}
