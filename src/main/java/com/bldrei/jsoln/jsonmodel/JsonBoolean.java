package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.util.ClassTree;

public record JsonBoolean(boolean value) implements JsonElement {

  public static final JsonBoolean TRUE = new JsonBoolean(true);
  public static final JsonBoolean FALSE = new JsonBoolean(false);

  public Object toObject(ClassTree classTree) {
    return value;
  }

  public String serialize() {
    return Boolean.toString(value);
  }

  @SuppressWarnings("unused")
  public static JsonBoolean from(Object flatValue, Class<?> clazz) {
    return switch (flatValue) {
      case Boolean b -> b ? TRUE : FALSE;
      default -> throw new IllegalStateException();
    };
  }
}

