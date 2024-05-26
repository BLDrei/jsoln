package com.bldrei.jsoln.converter.number;

public final class IntegerConverter extends NumberConverter<Integer> {

  public IntegerConverter() {
    super(Integer.class);
  }

  @Override
  public Integer convert(String value) {
    return Integer.valueOf(value);
  }
}
