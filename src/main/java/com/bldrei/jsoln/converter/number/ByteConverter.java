package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

public final class ByteConverter extends NumberConverter<Byte> {

  @Override
  public Byte convert(@NonNull String value) {
    return Byte.valueOf(value);
  }

  @Override
  protected String stringify(@NonNull Byte flatValue) {
    return Byte.toString(flatValue);
  }
}
