package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class FloatConverter extends NumberConverter<Float> {

  @Override
  public Float stringToObject(@NotNull String value) {
    return Float.valueOf(value);
  }

  @Override
  protected String stringify(@NotNull Float flatValue) {
    return Float.toString(flatValue);
  }
}
