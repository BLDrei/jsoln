package com.bldrei.jsoln.jsonmodel;

public abstract sealed class JsonElement
  permits JsonObject, JsonArray, JsonBoolean, JsonNull, JsonText, JsonNumber {
}
