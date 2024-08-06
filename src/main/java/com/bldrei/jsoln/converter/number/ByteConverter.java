package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class ByteConverter extends NumberConverter<Byte> {

  @Override
  public Byte stringToObject(@NotNull String value) {
    return Byte.valueOf(value);
  }

  @Override
  protected String stringify(@NotNull Byte flatValue) {
    return Byte.toString(flatValue);
  }
}
