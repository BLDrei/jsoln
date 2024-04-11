package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.converter.number.BigDecimalConverter;
import com.bldrei.jsoln.converter.number.BigIntegerConverter;
import com.bldrei.jsoln.converter.number.ByteConverter;
import com.bldrei.jsoln.converter.number.DoubleConverter;
import com.bldrei.jsoln.converter.number.FloatConverter;
import com.bldrei.jsoln.converter.number.IntegerConverter;
import com.bldrei.jsoln.converter.number.LongConverter;
import com.bldrei.jsoln.converter.number.ShortConverter;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

public final class JsonNumber extends JsonElement {
  private final String numberAsString;

  public JsonNumber(String numberAsString) {
    this.numberAsString = numberAsString;
  }

  public Number getNumericValue(Type rawType) {
    var converter = NUMBER_CONVERTERS.get((Class<?>) rawType);
    return Optional.ofNullable(converter)
      .orElseThrow(() -> new UnsupportedOperationException("Not implemented numeric class: " + rawType))
      .convert(numberAsString);
  }

  private static final Map<Class<? extends Number>, ? extends AbstractConverter<? extends Number>> NUMBER_CONVERTERS = Map.of(
    Integer.class, new IntegerConverter(),
    Long.class, new LongConverter(),
    Double.class, new DoubleConverter(),
    Float.class, new FloatConverter(),
    BigDecimal.class, new BigDecimalConverter(),
    BigInteger.class, new BigIntegerConverter(),
    Short.class, new ShortConverter(),
    Byte.class, new ByteConverter()
  );
}
