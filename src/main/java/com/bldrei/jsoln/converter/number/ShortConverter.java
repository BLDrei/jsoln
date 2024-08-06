package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class ShortConverter extends NumberConverter<Short> {

  @Override
  public Short stringToObject(@NotNull String value) {
    return Short.valueOf(value);
  }

  @Override
  protected String stringify(@NotNull Short flatValue) {
    return Short.toString(flatValue);
  }
}
