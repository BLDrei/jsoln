package com.bldrei.jsoln.number;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Getter
@Setter
public class AcceptedNumericParamsDto {
  private Optional<Float> _float;
  private Optional<Double> _double;
  private Optional<Byte> _byte;
  private Optional<Short> _short;
  private Optional<Integer> _integer;
  private Optional<Long> _long;
  private Optional<BigInteger> _bigInteger;
  private Optional<BigDecimal> _bigDecimal;
}
