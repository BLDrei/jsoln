package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class JsonNumber implements JsonElement {
  private final String numberAsString;

  public Object toObject(ClassTreeWithConverters classTree) {
    return ConvertersCache.getNumberConverter(classTree.rawType())
      .convert(numberAsString);
  }

  public StringBuffer appendToSB(StringBuffer sb) {
    return sb.append(numberAsString);
  }
}
