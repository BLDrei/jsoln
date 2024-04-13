package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.AbstractConverter;

import java.math.BigInteger;

public class BigIntegerConverter extends AbstractConverter<BigInteger> {

  public BigIntegerConverter() {
    super(BigInteger.class);
  }

  @Override
  public BigInteger convert(String value) {
    return new BigInteger(value);
  }
}
