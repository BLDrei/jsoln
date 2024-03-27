package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.AbstractConverter;

public class DoubleConverter extends AbstractConverter<Double> {

  public DoubleConverter() {
    super(Double.class);
  }

  @Override
  public Double convert(String value) {
    return Double.valueOf(value);
  }
}
