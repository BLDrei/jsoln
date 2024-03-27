package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.AbstractConverter;

import java.math.BigDecimal;

public class BigDecimalConverter extends AbstractConverter<BigDecimal> {

  public BigDecimalConverter() {
    super(BigDecimal.class);
  }

  @Override
  public BigDecimal convert(String value) {
    return new BigDecimal(value);
  }
}
