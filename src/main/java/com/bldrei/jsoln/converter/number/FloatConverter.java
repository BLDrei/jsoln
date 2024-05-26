package com.bldrei.jsoln.converter.number;

public final class FloatConverter extends NumberConverter<Float> {

  public FloatConverter() {
    super(Float.class);
  }

  @Override
  public Float convert(String value) {
    return Float.valueOf(value);
  }
}
