package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.converter.AbstractConverter;
import org.jetbrains.annotations.NotNull;

public abstract sealed class TextConverter<T>
  implements AbstractConverter
  permits LocalDateConverter, LocalDateTimeConverter, StringConverter, EnumConverter {

  public abstract T javaify(@NotNull String value);

  @SuppressWarnings("unchecked")
  public String stringify(@NotNull Object flatValue, StringBuilder sb) {
    return "\"" + (stringify((T) flatValue)) + "\"";
  }

  protected abstract String stringify(@NotNull T flatValue);
}
