package com.bldrei.jsoln.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

@UtilityClass
public class TypeUtil {

  public static boolean isOptional(Type type) {
    return type instanceof ParameterizedType parameterizedType
      && Optional.class.equals(parameterizedType.getRawType());
  }

  public static Type unwrapOptional(Type optional) {
    ParameterizedType parameterizedType = (ParameterizedType) optional;
    return parameterizedType.getActualTypeArguments()[0];
  }
}
