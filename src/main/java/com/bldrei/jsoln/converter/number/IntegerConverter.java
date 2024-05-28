package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

public final class IntegerConverter extends NumberConverter<Integer> {

  public IntegerConverter() {
    super(Integer.class);
  }

  @Override
  public Integer convert(String value) {
    return Integer.valueOf(value);
  }

  @Override
  protected String stringifyT(@NonNull Integer flatValue) {
    return Integer.toString(flatValue);
  }
}
