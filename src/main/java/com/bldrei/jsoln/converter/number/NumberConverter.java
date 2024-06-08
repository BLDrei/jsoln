package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.PlainTypeConverter;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import lombok.NonNull;

public abstract sealed class NumberConverter<N> extends PlainTypeConverter<N> permits ShortConverter, ByteConverter, IntegerConverter, LongConverter, DoubleConverter, FloatConverter, BigIntegerConverter, BigDecimalConverter {

  public abstract N convert(@NonNull String value);

  @SuppressWarnings("unchecked")
  public JsonElement objectToJsonElement(@NonNull Object flatValue) {
    return new JsonNumber(stringify((N) flatValue));
  }
}
