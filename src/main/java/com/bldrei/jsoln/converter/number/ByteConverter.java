package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

public final class ByteConverter extends NumberConverter<Byte> {

  public ByteConverter() {
    super(Byte.class);
  }

  @Override
  public Byte convert(String value) {
    return Byte.valueOf(value);
  }

  @Override
  protected String stringifyT(@NonNull Byte flatValue) {
    return Byte.toString(flatValue);
  }
}
