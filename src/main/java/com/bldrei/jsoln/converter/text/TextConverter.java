package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.converter.PlainTypeConverter;

public abstract sealed class TextConverter<T> extends PlainTypeConverter<T> permits LocalDateConverter, LocalDateTimeConverter, StringConverter, EnumConverter {
  protected TextConverter(Class<T> type) {
    super(type);
  }

  public abstract T convert(String value);
}
