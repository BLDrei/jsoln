package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.util.ClassTree;

public sealed interface JsonElement
  permits JsonObject, JsonArray, JsonBoolean, JsonText, JsonNumber {

  Object toObject(ClassTree classTree);

  String serialize(); //todo: change to appendToSB

  @SuppressWarnings("unused")
  default JsonDataType getJsonDataType() {
    return switch (this) {
      case JsonArray jsonArray -> JsonDataType.ARRAY;
      case JsonBoolean jsonBoolean -> JsonDataType.BOOLEAN;
      case JsonNumber jsonNumber -> JsonDataType.NUMBER;
      case JsonObject jsonObject -> JsonDataType.OBJECT;
      case JsonText jsonText -> JsonDataType.TEXT;
    };
  }
}
