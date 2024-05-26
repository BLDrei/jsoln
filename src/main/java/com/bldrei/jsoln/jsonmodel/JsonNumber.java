package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.cache.ConvertersCache;

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
}
