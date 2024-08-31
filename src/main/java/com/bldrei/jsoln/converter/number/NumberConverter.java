package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import org.jetbrains.annotations.NotNull;

public abstract sealed class NumberConverter<N>
  implements AbstractConverter
  permits ShortConverter, ByteConverter, IntegerConverter, LongConverter, DoubleConverter, FloatConverter, BigIntegerConverter, BigDecimalConverter {

  public abstract N stringToObject(@NotNull String value);

  @SuppressWarnings("unchecked")
  public JsonElement numberTypeToJsonNumber(@NotNull Object flatValue) {
    return new JsonNumber(stringify((N) flatValue));
  }

  protected abstract String stringify(@NotNull N flatValue);
}
