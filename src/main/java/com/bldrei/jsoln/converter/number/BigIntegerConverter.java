package com.bldrei.jsoln.converter.number;

import lombok.NonNull;

import java.math.BigInteger;

public final class BigIntegerConverter extends NumberConverter<BigInteger> {

  @Override
  public BigInteger convert(@NonNull String value) {
    return new BigInteger(value);
  }

  @Override
  protected String stringify(@NonNull BigInteger bi) {
    return bi.toString();
  }
}
