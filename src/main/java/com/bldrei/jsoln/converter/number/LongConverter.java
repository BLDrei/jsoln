package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class LongConverter extends NumberConverter<Long> {

  @Override
  public Long stringToObject(@NotNull String value) {
    return Long.valueOf(value);
  }

  @Override
  protected String stringify(@NotNull Long flatValue) {
    return Long.toString(flatValue);
  }
}
