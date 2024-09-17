package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public final class BigIntegerConverter extends NumberConverter<BigInteger> {
  @Override
  public BigInteger javaify(@NotNull Number value) {
    return switch (value) {
      case Long l -> BigInteger.valueOf(l);
      case BigInteger bi -> bi;
      default -> new BigInteger(value.toString()); //only Double & BigDecimal, which should throw casting exceptions
    };
  }

  @Override
  protected Number nToLongOrBigIntOrDoubleOrBigDecimal(@NotNull BigInteger value) {
    return value;
  }
}
