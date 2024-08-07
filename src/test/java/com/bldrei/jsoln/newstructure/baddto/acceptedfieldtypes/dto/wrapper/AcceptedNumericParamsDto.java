package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Deprecated
public record AcceptedNumericParamsDto(Optional<Float> _float, Optional<Double> _double, Optional<Byte> _byte,
                                       Optional<Short> _short, Optional<Integer> _integer, Optional<Long> _long,
                                       Optional<BigInteger> _bigInteger, Optional<BigDecimal> _bigDecimal) {
}
