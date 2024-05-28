package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.cache.ConvertersCache;
import lombok.Getter;

@Getter
public final class JsonText implements JsonElement {
  private final String valueAsString;

  public JsonText(String valueAsString) {
    this.valueAsString = valueAsString;
  }

  public <T> T getValue(Class<T> clazz) {
    return ConvertersCache.getTextConverter(clazz)
      .orElseThrow(() -> new UnsupportedOperationException("Cannot deserialize text '\"" + valueAsString + "\"' to: " + clazz))
      .convert(valueAsString);
  }

  public static JsonText from(Object flatValue, Class<?> clazz) {
    var converter = ConvertersCache.getTextConverter(clazz).orElseThrow(IllegalStateException::new);
    return new JsonText(converter.stringify(flatValue));
  }
}
