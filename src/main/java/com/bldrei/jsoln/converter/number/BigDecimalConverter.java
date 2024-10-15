package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public final class BigDecimalConverter extends NumberConverter<BigDecimal> {
  @Override
  public BigDecimal javaify(@NotNull String value) {
    return new BigDecimal(value);
  }
}
