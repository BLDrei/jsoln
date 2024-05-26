package com.bldrei.jsoln.converter.number;

public final class LongConverter extends NumberConverter<Long> {

  public LongConverter() {
    super(Long.class);
  }

  @Override
  public Long convert(String value) {
    return Long.valueOf(value);
  }
}
