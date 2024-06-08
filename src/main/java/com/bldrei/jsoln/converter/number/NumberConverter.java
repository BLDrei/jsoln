package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import lombok.NonNull;

public abstract sealed class NumberConverter<N>
  implements AbstractConverter
  permits ShortConverter, ByteConverter, IntegerConverter, LongConverter, DoubleConverter, FloatConverter, BigIntegerConverter, BigDecimalConverter {

  public abstract N stringToObject(@NonNull String value);

  @SuppressWarnings("unchecked")
  public JsonElement objectToJsonElement(@NonNull Object flatValue) {
    return new JsonNumber(stringify((N) flatValue));
  }

  protected abstract String stringify(@NonNull N flatValue);
}
