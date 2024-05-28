package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

import java.math.BigDecimal;

public final class BigDecimalConverter extends NumberConverter<BigDecimal> {

  public BigDecimalConverter() {
    super(BigDecimal.class);
  }

  @Override
  public BigDecimal convert(String value) {
    return new BigDecimal(value);
  }

  @Override
  protected String stringifyT(@NonNull BigDecimal bd) {
    return bd.toString();
  }
}
