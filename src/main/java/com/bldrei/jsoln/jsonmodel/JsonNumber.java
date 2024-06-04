package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.util.ClassTree;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class JsonNumber implements JsonElement {
  private final String numberAsString;

  public Object toObject(ClassTree classTree) {
    return ConvertersCache.getNumberConverter(classTree.rawType())
      .orElseThrow(IllegalStateException::new)
      .convert(numberAsString);
  }

  public static <N> JsonNumber from(Object flatValue, Class<N> clazz) {
    var converter = ConvertersCache.getNumberConverter(clazz).orElseThrow(IllegalStateException::new);
    return new JsonNumber(converter.stringify(flatValue));
  }
}
