package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.AbstractConverter;
import org.jetbrains.annotations.NotNull;

public abstract sealed class NumberConverter<N>
  implements AbstractConverter
  permits ShortConverter, ByteConverter, IntegerConverter, LongConverter, DoubleConverter, FloatConverter, BigIntegerConverter, BigDecimalConverter {

  public abstract N javaify(@NotNull Number value);

  @SuppressWarnings("unchecked")
  public Number toJsonModel(@NotNull Object value) {
    return nToLongOrBigIntOrDoubleOrBigDecimal((N) value);
  }

  protected abstract Number nToLongOrBigIntOrDoubleOrBigDecimal(@NotNull N value);
}
