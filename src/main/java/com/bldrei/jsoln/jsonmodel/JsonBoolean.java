package com.bldrei.jsoln.jsonmodel;

public record JsonBoolean(boolean value) implements JsonElement {

  public static final JsonBoolean TRUE = new JsonBoolean(true);
  public static final JsonBoolean FALSE = new JsonBoolean(false);
}

