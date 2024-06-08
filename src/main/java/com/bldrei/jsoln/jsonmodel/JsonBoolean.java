package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.util.ClassTreeWithConverters;

public record JsonBoolean(boolean value) implements JsonElement {

  public static final JsonBoolean TRUE = new JsonBoolean(true);
  public static final JsonBoolean FALSE = new JsonBoolean(false);

  public Object toObject(ClassTreeWithConverters classTree) {
    return value;
  }

  public StringBuffer appendToSB(StringBuffer sb) {
    return sb.append(value);
  }
}

