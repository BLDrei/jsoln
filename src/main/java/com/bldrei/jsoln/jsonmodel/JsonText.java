package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.converter.text.EnumConverter;
import com.bldrei.jsoln.converter.text.LocalDateConverter;
import com.bldrei.jsoln.converter.text.LocalDateTimeConverter;
import com.bldrei.jsoln.converter.text.StringConverter;
import com.bldrei.jsoln.converter.text.TextConverter;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public final class JsonText implements JsonElement {
  private final String value;

  public JsonText(String value) {
    this.value = value;
  }

  public Object getValue(Type actualType) {
    Class<?> clazz = (Class<?>) actualType;
    return getTextConverterPerClass(clazz)
      .orElseThrow(() -> new UnsupportedOperationException("Cannot deserialize text '\"" + value + "\"' to: " + clazz))
      .convert(value);
  }

  private static final Map<Class<?>, ? extends TextConverter<?>> TEXT_CONVERTERS = Map.of(
    String.class, new StringConverter(),
    LocalDate.class, new LocalDateConverter(),
    LocalDateTime.class, new LocalDateTimeConverter()
  );

  private static Optional<TextConverter<?>> getTextConverterPerClass(Class<?> clazz) {
    if (clazz.isEnum()) {
      return Optional.of(new EnumConverter<>(clazz)); //todo: cache
    }
    return Optional.ofNullable(TEXT_CONVERTERS.get(clazz));
  }
}
