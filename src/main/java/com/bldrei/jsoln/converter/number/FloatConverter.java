package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

public final class FloatConverter extends NumberConverter<Float> {

  @Override
  public Float stringToObject(@NonNull String value) {
    return Float.valueOf(value);
  }

  @Override
  protected String stringify(@NonNull Float flatValue) {
    return Float.toString(flatValue);
  }
}
