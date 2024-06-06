package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.Const;
import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.util.ClassTree;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public final class JsonArray implements JsonElement {

  private final List<JsonElement> array;

  public Object toObject(ClassTree classTree) {
    return ConvertersCache.getArrayConverter(classTree.rawType())
      .jsonElementsToObject(array, classTree);
  }

  public String serialize() {
    return array
      .stream()
      .map(JsonElement::serialize)
      .collect(Collectors.joining(Const.ARRAY_MEMBERS_DELIMITER_STR, Const.OPENING_BRACKET_STR, Const.CLOSING_BRACKET_STR));
  }
}
