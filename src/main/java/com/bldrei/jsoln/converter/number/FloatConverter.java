package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.AbstractConverter;

public class FloatConverter extends AbstractConverter<Float> {

  public FloatConverter() {
    super(Float.class);
  }

  @Override
  public Float convert(String value) {
    return Float.valueOf(value);
  }
}
