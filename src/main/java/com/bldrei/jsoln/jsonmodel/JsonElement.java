package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.util.ClassTree;

public sealed interface JsonElement
  permits JsonObject, JsonArray, JsonBoolean, JsonText, JsonNumber {

  Object toObject(ClassTree classTree);

  StringBuffer appendToSB(StringBuffer sb);

  enum Type {
    ARRAY, BOOLEAN, NUMBER, OBJECT, TEXT
  }

  @SuppressWarnings("unused")
  default JsonElement.Type getJsonDataType() {
    return switch (this) {
      case JsonArray jsonArray -> Type.ARRAY;
      case JsonBoolean jsonBoolean -> Type.BOOLEAN;
      case JsonNumber jsonNumber -> Type.NUMBER;
      case JsonObject jsonObject -> Type.OBJECT;
      case JsonText jsonText -> Type.TEXT;
    };
  }
}
