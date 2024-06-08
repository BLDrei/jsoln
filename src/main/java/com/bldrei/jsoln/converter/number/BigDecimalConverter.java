package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

import java.math.BigDecimal;

public final class BigDecimalConverter extends NumberConverter<BigDecimal> {

  @Override
  public BigDecimal stringToObject(@NonNull String value) {
    return new BigDecimal(value);
  }

  @Override
  protected String stringify(@NonNull BigDecimal bd) {
    return bd.toString();
  }
}
