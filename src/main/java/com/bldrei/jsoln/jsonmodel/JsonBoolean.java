package com.bldrei.jsoln.jsonmodel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class JsonBoolean extends JsonElement {

  private final boolean value;

  public static final JsonBoolean TRUE = new JsonBoolean(true);
  public static final JsonBoolean FALSE = new JsonBoolean(false);

  public boolean getValue() {
    return value;
  }
}

