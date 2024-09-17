package com.bldrei.jsoln.jsonmodel;

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

  public static JsonModelType determineJsonModelTypeFromStringTreeModel(Class<?> clazz) {
    return Arrays.stream(JsonModelType.values())
      .filter(jmt -> jmt.getStringTreeRepresentation().contains(clazz))
      .findFirst()
      .orElseThrow();
  }
}
