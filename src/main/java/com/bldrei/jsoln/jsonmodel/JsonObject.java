package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.util.ClassTree;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class JsonObject implements JsonElement {
  Map<String, JsonElement> kvMap;

  public JsonObject(Map<String, JsonElement> kvMap) {
    this.kvMap = kvMap;
  }

  public Optional<JsonElement> get(String fieldName) {
    return Optional.ofNullable(kvMap.get(fieldName));
  }

  public Object getValue(ClassTree classTree) {
    if (Map.class.equals(classTree.rawType())) {
      if (!String.class.equals(classTree.genericParameters()[0])) {
        throw new RuntimeException("Not implemented yet");
      }
      return kvMap.entrySet().stream()
        .collect(Collectors.toUnmodifiableMap(
          Map.Entry::getKey,
          e -> Jsoln.extractValueFromJsonElement(e.getValue(), ClassTree.fromType(classTree.genericParameters()[1])),
          (k1, k2) -> new IllegalStateException("Duplicate keys %s and %s".formatted(k1, k2))
        ));
    }
    return Jsoln.deserialize(this, (Class<?>) classTree.rawType());
  }
}
