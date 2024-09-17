package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class BigDecimalConverter extends NumberConverter<BigDecimal> {
  @Override
  public BigDecimal javaify(@NotNull Number value) {
    return switch (value) {
      case Double d -> BigDecimal.valueOf(d);
      case BigDecimal bd -> bd;
      case Long l -> new BigDecimal(l);
      case BigInteger bi -> new BigDecimal(bi);
      default -> new BigDecimal(value.toString());
    };
  }

  @Override
  protected Number nToLongOrBigIntOrDoubleOrBigDecimal(@NotNull BigDecimal value) {
    return value;
  }
}
