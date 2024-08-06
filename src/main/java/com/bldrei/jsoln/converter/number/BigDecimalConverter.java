package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public final class BigDecimalConverter extends NumberConverter<BigDecimal> {

  @Override
  public BigDecimal stringToObject(@NotNull String value) {
    return new BigDecimal(value);
  }

  @Override
  protected String stringify(@NotNull BigDecimal bd) {
    return bd.toString();
  }
}
