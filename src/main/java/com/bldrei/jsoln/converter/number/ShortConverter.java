package com.bldrei.jsoln.converter.number;

public final class ShortConverter extends NumberConverter<Short> {

  public ShortConverter() {
    super(Short.class);
  }

  @Override
  public Short convert(String value) {
    return Short.valueOf(value);
  }
}
