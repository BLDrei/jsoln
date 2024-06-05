package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.Const;
import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.util.ClassTree;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public final class JsonArray implements JsonElement {

  private final List<JsonElement> array;

  public Object toObject(ClassTree classTree) {
    Class<?> collectionClass = classTree.rawType();
    ClassTree actualTypeTree = classTree.genericParameters()[0];
    Stream<?> stream = array.stream()
      .map(jsonElement -> jsonElement.toObject(actualTypeTree));

    return ConvertersCache.getArrayConverter(collectionClass)
      .orElseThrow(() -> new IllegalArgumentException("Unexpected collection type: " + collectionClass))
      .convert(stream);
  }

  public String serialize() {
    return array
      .stream()
      .map(JsonElement::serialize)
      .collect(Collectors.joining(Const.ARRAY_MEMBERS_DELIMITER_STR, Const.OPENING_BRACKET_STR, Const.CLOSING_BRACKET_STR));
  }
}
