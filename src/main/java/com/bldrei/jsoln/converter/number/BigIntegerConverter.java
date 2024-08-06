package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public final class BigIntegerConverter extends NumberConverter<BigInteger> {

  @Override
  public BigInteger stringToObject(@NotNull String value) {
    return new BigInteger(value);
  }

  @Override
  protected String stringify(@NotNull BigInteger bi) {
    return bi.toString();
  }
}
