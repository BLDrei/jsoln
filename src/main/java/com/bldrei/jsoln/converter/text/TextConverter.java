package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonText;
import lombok.NonNull;

public abstract sealed class TextConverter<T>
  implements AbstractConverter
  permits LocalDateConverter, LocalDateTimeConverter, StringConverter, EnumConverter {

  public abstract T stringToObject(@NonNull String value);

  @SuppressWarnings("unchecked")
  public JsonElement objectToJsonElement(@NonNull Object flatValue) {
    return new JsonText(stringify((T) flatValue));
  }

  protected abstract String stringify(@NonNull T flatValue);
}
