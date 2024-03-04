package com.bldrei.jsoln.jsonmodel;

import java.util.Map;

public final class JsonObject extends JsonElement {
  Map<String, JsonElement> kvMap;

  public JsonObject(Map<String, JsonElement> kvMap) {
    this.kvMap = kvMap;
  }

  public boolean hasField(String fieldName) {
    return kvMap.containsKey(fieldName);
  }

  public JsonElement get(String fieldName) {
    return kvMap.get(fieldName);
  }
}
