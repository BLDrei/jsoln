package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.cache.ConvertersCache;

public final class JsonText implements JsonElement {
  private final String value;

  public JsonText(String value) {
    this.value = value;
  }

  public Object getValue(Class<?> clazz) {
    return ConvertersCache.getTextConverter(clazz)
      .orElseThrow(() -> new UnsupportedOperationException("Cannot deserialize text '\"" + value + "\"' to: " + clazz))
      .convert(value);
  }
}
