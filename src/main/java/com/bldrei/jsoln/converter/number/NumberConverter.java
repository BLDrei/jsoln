package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.PlainTypeConverter;

public abstract sealed class NumberConverter<N extends Number> extends PlainTypeConverter<N> permits ShortConverter, ByteConverter, IntegerConverter, LongConverter, DoubleConverter, FloatConverter, BigIntegerConverter, BigDecimalConverter {
  protected NumberConverter(Class<N> type) {
    super(type);
  }

  public abstract N convert(String value);
}
