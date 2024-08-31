package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonText;
import org.jetbrains.annotations.NotNull;

public abstract sealed class TextConverter<T>
  implements AbstractConverter
  permits LocalDateConverter, LocalDateTimeConverter, StringConverter, EnumConverter {

  public abstract T stringToObject(@NotNull String value);

  @SuppressWarnings("unchecked")
  public JsonElement textTypeToJsonText(@NotNull Object flatValue) {
    return new JsonText(stringify((T) flatValue));
  }

  protected abstract String stringify(@NotNull T flatValue);
}
