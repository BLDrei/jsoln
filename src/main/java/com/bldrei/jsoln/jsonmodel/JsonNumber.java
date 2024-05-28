package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.cache.ConvertersCache;
import lombok.Getter;

@Getter
public final class JsonNumber implements JsonElement {
  private final String numberAsString;

  public JsonNumber(String numberAsString) {
    this.numberAsString = numberAsString;
  }

  public <N extends Number> N getNumericValue(Class<N> clazz) {
    return ConvertersCache.getNumberConverter(clazz)
      .orElseThrow(() -> new UnsupportedOperationException("Not implemented numeric class: " + clazz))
      .convert(numberAsString);
  }

  public static <N extends Number> JsonNumber from(Object flatValue, Class<N> clazz) {
    var converter = ConvertersCache.getNumberConverter(clazz).orElseThrow(IllegalStateException::new);
    return new JsonNumber(converter.stringify(flatValue));
  }
}
