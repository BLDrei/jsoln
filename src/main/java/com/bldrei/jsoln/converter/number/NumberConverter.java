package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.PlainTypeConverter;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import lombok.NonNull;

public abstract sealed class NumberConverter<N> extends PlainTypeConverter<N> permits ShortConverter, ByteConverter, IntegerConverter, LongConverter, DoubleConverter, FloatConverter, BigIntegerConverter, BigDecimalConverter {
  protected NumberConverter(Class<N> type) {
    super(type);
  }

  public abstract N convert(String value);

  @SuppressWarnings("unchecked")
  public JsonElement objectToJsonElement(@NonNull Object flatValue) {
    return new JsonNumber(stringifyT((N) flatValue));
  }
}
