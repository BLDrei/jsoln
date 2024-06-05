package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.util.ClassTree;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class JsonNumber implements JsonElement {
  private final String numberAsString;

  public Object toObject(ClassTree classTree) {
    return ConvertersCache.getNumberConverter(classTree.rawType())
      .orElseThrow(IllegalStateException::new)
      .convert(numberAsString);
  }

  public String serialize() {
    return numberAsString;
  }
}
