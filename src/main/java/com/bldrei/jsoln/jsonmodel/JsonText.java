package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.util.ClassTree;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class JsonText implements JsonElement {
  private final String valueAsString;

  public Object toObject(ClassTree classTree) {
    return ConvertersCache.getTextConverter(classTree.rawType())
      .orElseThrow(IllegalStateException::new)
      .convert(valueAsString);
  }

  public static JsonText from(Object flatValue, Class<?> clazz) {
    var converter = ConvertersCache.getTextConverter(clazz).orElseThrow(IllegalStateException::new);
    return new JsonText(converter.stringify(flatValue));
  }
}
