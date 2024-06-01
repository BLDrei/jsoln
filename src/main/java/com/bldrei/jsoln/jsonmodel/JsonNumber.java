package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.cache.ConvertersCache;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class JsonNumber implements JsonElement {
  private final String numberAsString;

  public <N> N getNumericValue(Class<N> clazz) {
    return ConvertersCache.getNumberConverter(clazz)
      .orElseThrow(() -> new UnsupportedOperationException("Not implemented numeric class: " + clazz))
      .convert(numberAsString);
  }

  public static <N> JsonNumber from(Object flatValue, Class<N> clazz) {
    var converter = ConvertersCache.getNumberConverter(clazz).orElseThrow(IllegalStateException::new);
    return new JsonNumber(converter.stringify(flatValue));
  }
}
