package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

public final class FloatConverter extends NumberConverter<Float> {

  public FloatConverter() {
    super(Float.class);
  }

  @Override
  public Float convert(String value) {
    return Float.valueOf(value);
  }

  @Override
  protected String stringifyT(@NonNull Float flatValue) {
    return Float.toString(flatValue);
  }
}
