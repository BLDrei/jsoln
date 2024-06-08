package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

public final class ShortConverter extends NumberConverter<Short> {

  @Override
  public Short stringToObject(@NonNull String value) {
    return Short.valueOf(value);
  }

  @Override
  protected String stringify(@NonNull Short flatValue) {
    return Short.toString(flatValue);
  }
}
