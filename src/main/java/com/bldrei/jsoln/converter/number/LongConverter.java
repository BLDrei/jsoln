package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.AbstractConverter;

public class LongConverter extends AbstractConverter<Long> {

  public LongConverter() {
    super(Long.class);
  }

  @Override
  public Long convert(String value) {
    return Long.valueOf(value);
  }
}
