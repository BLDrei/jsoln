package com.bldrei.jsoln.jsonmodel;

public sealed interface JsonElement
  permits JsonObject, JsonArray, JsonBoolean, JsonText, JsonNumber {

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
