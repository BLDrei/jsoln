package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

import java.math.BigInteger;

public final class BigIntegerConverter extends NumberConverter<BigInteger> {

  public BigIntegerConverter() {
    super(BigInteger.class);
  }

  @Override
  public BigInteger convert(String value) {
    return new BigInteger(value);
  }

  @Override
  protected String stringifyT(@NonNull BigInteger bi) {
    return bi.toString();
  }
}
