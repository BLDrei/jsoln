package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

public final class IntegerConverter extends NumberConverter<Integer> {

  @Override
  public Integer stringToObject(@NonNull String value) {
    return Integer.valueOf(value);
  }

  @Override
  protected String stringify(@NonNull Integer flatValue) {
    return Integer.toString(flatValue);
  }
}
