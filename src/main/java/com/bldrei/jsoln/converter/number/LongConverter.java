package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

public final class LongConverter extends NumberConverter<Long> {

  @Override
  public Long convert(@NonNull String value) {
    return Long.valueOf(value);
  }

  @Override
  protected String stringify(@NonNull Long flatValue) {
    return Long.toString(flatValue);
  }
}
