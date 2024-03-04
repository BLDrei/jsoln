package com.bldrei.jsoln.jsonmodel;

public final class JsonText extends JsonElement {
  private final String value;

  public JsonText(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
