package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.util.ClassTree;
import com.bldrei.jsoln.util.SerializeUtil;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bldrei.jsoln.Jsoln.extractValueFromJsonElement;

@Getter
public final class JsonArray implements JsonElement {

  private final List<JsonElement> array;

  public JsonArray(List<JsonElement> array) {
    this.array = array;
  }

  public Collection<?> getCollection(ClassTree classTree) {
    Class<?> collectionClass = classTree.rawType();
    ClassTree actualType = classTree.genericParameters()[0];
    Stream<?> stream = array.stream()
      .map(jsonElement -> extractValueFromJsonElement(jsonElement, actualType));

    if (List.class.equals(collectionClass)) {
      return stream.toList();
    }
    if (Set.class.equals(collectionClass)) {
      return stream.collect(Collectors.toUnmodifiableSet());
    }
    throw new IllegalArgumentException("Unexpected collection type: " + collectionClass);
  }

  public static JsonArray from(Object collection, ClassTree classTree) {
    ClassTree collectionOfWhat = classTree.genericParameters()[0];
    List<JsonElement> jsonElements = switch (collection) {
      case List<?> l -> l.stream()
        .map(it -> SerializeUtil.convertObjectToJsonElement(it, collectionOfWhat))
        .toList();
      case Set<?> s -> s.stream()
        .map(it -> SerializeUtil.convertObjectToJsonElement(it, collectionOfWhat))
        .toList();
      default -> throw new IllegalStateException();
    };
    return new JsonArray(jsonElements);
  }
}
