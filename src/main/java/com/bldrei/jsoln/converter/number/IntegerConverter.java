package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.AbstractConverter;

public class IntegerConverter extends AbstractConverter<Integer> {

  public IntegerConverter() {
    super(Integer.class);
  }

  @Override
  public Integer convert(String value) {
    return Integer.valueOf(value);
  }
}
