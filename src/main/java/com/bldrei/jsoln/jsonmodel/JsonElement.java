package com.bldrei.jsoln.jsonmodel;

public sealed interface JsonElement
  permits JsonObject, JsonArray, JsonBoolean, JsonText, JsonNumber {
}
