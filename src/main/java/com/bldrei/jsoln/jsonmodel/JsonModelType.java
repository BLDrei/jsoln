package com.bldrei.jsoln.jsonmodel;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum JsonModelType {
  ARRAY(ArrayList.class),
  BOOLEAN(Boolean.class),
  NUMBER(Set.of(Long.class, BigInteger.class, Double.class, BigDecimal.class)),
  OBJECT(HashMap.class),
  TEXT(String.class);

  private final Set<Class<?>> stringTreeRepresentation;

  JsonModelType(Class<?> clazz) {
    this(Collections.singleton(clazz));
  }

  public static JsonModelType determineJsonModelTypeFromJsonNode(JsonNode jsonNode) {
    if (jsonNode.isObject()) return OBJECT;
    if (jsonNode.isArray()) return ARRAY;
    if (jsonNode.isBoolean()) return BOOLEAN;
    if (jsonNode.isTextual()) return TEXT;
    if (jsonNode.isNumber()) return NUMBER;
    throw new IllegalArgumentException(jsonNode.getNodeType().name());
  }
}
