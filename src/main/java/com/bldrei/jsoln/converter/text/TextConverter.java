package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.configuration.Configuration;
import com.bldrei.jsoln.converter.AbstractConverter;
import org.jetbrains.annotations.NotNull;

public abstract sealed class TextConverter<T>
  implements AbstractConverter
  permits LocalDateConverter, LocalDateTimeConverter, StringConverter, EnumConverter {

  public abstract T javaify(@NotNull String value, @NotNull Configuration conf);

  @SuppressWarnings("unchecked")
  public String stringify(@NotNull Object flatValue, StringBuilder sb, Configuration conf) {
    return "\"" + (stringify((T) flatValue, conf)) + "\"";
  }

  protected abstract String stringify(@NotNull T flatValue, Configuration conf);
}
