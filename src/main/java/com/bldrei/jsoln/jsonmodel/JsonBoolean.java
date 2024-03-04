package com.bldrei.jsoln.jsonmodel;

public final class JsonBoolean extends JsonElement {

  private final boolean value;

  public JsonBoolean(boolean value) {
    this.value = value;
  }

  public boolean getValue() {
    return value;
  }
}

