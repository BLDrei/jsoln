package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.Configuration;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public final class BigIntegerConverter extends NumberConverter<BigInteger> {
  @Override
  public BigInteger javaify(@NotNull String value, @NotNull Configuration conf) {
    return new BigInteger(value);
  }
}
