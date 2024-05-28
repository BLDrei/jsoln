package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

public final class ShortConverter extends NumberConverter<Short> {

  public ShortConverter() {
    super(Short.class);
  }

  @Override
  public Short convert(String value) {
    return Short.valueOf(value);
  }

  @Override
  protected String stringifyT(@NonNull Short flatValue) {
    return Short.toString(flatValue);
  }
}
