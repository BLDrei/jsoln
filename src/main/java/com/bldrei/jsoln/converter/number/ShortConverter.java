package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.AbstractConverter;

public class ShortConverter extends AbstractConverter<Short> {

  public ShortConverter() {
    super(Short.class);
  }

  @Override
  public Short convert(String value) {
    return Short.valueOf(value);
  }
}
