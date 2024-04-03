package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.converter.text.LocalDateConverter;
import com.bldrei.jsoln.converter.text.LocalDateTimeConverter;
import com.bldrei.jsoln.converter.text.StringConverter;

import java.lang.reflect.Type;
import java.util.List;

public final class JsonText extends JsonElement {
  private final String value;

  public JsonText(String value) {
    this.value = value;
  }

  public Object getValue(Type actualType) {
    return TEXT_CONVERTERS.stream()
      .filter(c -> c.getType().equals(actualType))
      .findAny()
      .map(c -> c.convert(value))
      .orElseThrow(() -> new UnsupportedOperationException("Not implemented text class: " + actualType));
  }

  private static final List<? extends AbstractConverter> TEXT_CONVERTERS = List.of(
    new StringConverter(),
    new LocalDateConverter(),
    new LocalDateTimeConverter()
  );
}
