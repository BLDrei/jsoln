package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.util.ClassTree;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bldrei.jsoln.Jsoln.extractValueFromJsonElement;

public final class JsonArray extends JsonElement {

  private final List<JsonElement> array;

  public JsonArray(List<JsonElement> array) {
    this.array = array;
  }

  public Collection<?> getCollection(ClassTree classTree) {
    Class<?> collectionClass = (Class<?>) classTree.rawType();
    Class<?> actualType = (Class<?>) classTree.genericParameters()[0];
    Stream<?> stream = array.stream()
      .map(jsonElement -> extractValueFromJsonElement(jsonElement, ClassTree.fromType(actualType)));

    if (List.class.equals(collectionClass)) {
      return stream.toList();
    }
    if (Set.class.equals(collectionClass)) {
      return stream.collect(Collectors.toUnmodifiableSet());
    }
    throw new IllegalArgumentException("Unexpected collection type: " + collectionClass);
  }
}
