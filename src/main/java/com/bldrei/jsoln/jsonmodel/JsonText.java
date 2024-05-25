package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.cache.Cache;
import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.converter.text.LocalDateConverter;
import com.bldrei.jsoln.converter.text.LocalDateTimeConverter;
import com.bldrei.jsoln.converter.text.StringConverter;
import com.bldrei.jsoln.util.ReflectionUtil;

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
    if (clazz.isEnum()) {
      return ReflectionUtil.invokeStaticMethod(Cache.getEnumValueOf(clazz), value);
    }
    var converter = TEXT_CONVERTERS.get(actualType);
    return Optional.ofNullable(converter)
      .orElseThrow(() -> new UnsupportedOperationException("Not implemented text class: " + actualType))
      .convert(value);
  }

  private static final Map<Class<?>, ? extends AbstractConverter<?>> TEXT_CONVERTERS = Map.of(
    String.class, new StringConverter(),
    LocalDate.class, new LocalDateConverter(),
    LocalDateTime.class, new LocalDateTimeConverter()
  );
}
