package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.AbstractConverter;

public class ByteConverter extends AbstractConverter<Byte> {

  public ByteConverter() {
    super(Byte.class);
  }

  @Override
  public Byte convert(String value) {
    return Byte.valueOf(value);
  }
}
