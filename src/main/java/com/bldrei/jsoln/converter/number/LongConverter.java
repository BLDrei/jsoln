package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

public final class LongConverter extends NumberConverter<Long> {

  public LongConverter() {
    super(Long.class);
  }

  @Override
  public Long convert(String value) {
    return Long.valueOf(value);
  }

  @Override
  protected String stringifyT(@NonNull Long flatValue) {
    return Long.toString(flatValue);
  }
}
