package com.bldrei.jsoln.converter;

import lombok.NonNull;

public class BooleanConverter extends PlainTypeConverter<Boolean> {

  public BooleanConverter() {
    super(Boolean.class);
  }

  @Override
  protected String stringifyT(@NonNull Boolean flatValue) {
    return flatValue.toString();
  }
}
