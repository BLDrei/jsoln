package com.bldrei.jsoln.jsonmodel;

public final class JsonNumber extends JsonElement {
  private final String numberAsString;

  public JsonNumber(String numberAsString) {
    this.numberAsString = numberAsString;
  }


  public String getNumberAsString() {
    return numberAsString;
  }
}
