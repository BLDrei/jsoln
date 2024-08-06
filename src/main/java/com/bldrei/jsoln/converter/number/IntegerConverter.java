package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class IntegerConverter extends NumberConverter<Integer> {

  @Override
  public Integer stringToObject(@NotNull String value) {
    return Integer.valueOf(value);
  }

  @Override
  protected String stringify(@NotNull Integer flatValue) {
    return Integer.toString(flatValue);
  }
}
