package com.bldrei.jsoln.jsonmodel;

import java.util.List;

public final class JsonArray extends JsonElement {

  private final List<JsonElement> array;

  public JsonArray(List<JsonElement> array) {
    this.array = array;
  }

  public boolean hasNext() {
    return array.getFirst() != null;
  }

  public JsonElement next() {
    return array.removeFirst();
  }
}
