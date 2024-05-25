package com.bldrei.jsoln.number;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

public record AcceptedNumericParamsDto(Optional<Float> _float, Optional<Double> _double, Optional<Byte> _byte,
                                       Optional<Short> _short, Optional<Integer> _integer, Optional<Long> _long,
                                       Optional<BigInteger> _bigInteger, Optional<BigDecimal> _bigDecimal) {
}
