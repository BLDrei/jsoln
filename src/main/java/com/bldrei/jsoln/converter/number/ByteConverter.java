package com.bldrei.jsoln.converter.number;

public final class ByteConverter extends NumberConverter<Byte> {

  public ByteConverter() {
    super(Byte.class);
  }

  @Override
  public Byte convert(String value) {
    return Byte.valueOf(value);
  }
}
