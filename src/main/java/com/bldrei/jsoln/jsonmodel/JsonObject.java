package com.bldrei.jsoln.jsonmodel;

import java.util.Map;
import java.util.Optional;

public final class JsonObject extends JsonElement {
  Map<String, JsonElement> kvMap;

  public JsonObject(Map<String, JsonElement> kvMap) {
    this.kvMap = kvMap;
  }

  public Optional<JsonElement> get(String fieldName) {
    return Optional.ofNullable(kvMap.get(fieldName));
  }
}
