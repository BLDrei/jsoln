package com.bldrei.jsoln.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

public record ClassTree(Class<?> rawType, ClassTree[] genericParameters) { //todo: rename, it's probably not a Tree

  public static final ClassTree[] NO_TYPES = new ClassTree[]{};

  public ClassTree {
    Objects.requireNonNull(rawType);
    Objects.requireNonNull(genericParameters);
  }

  public static ClassTree fromField(Field fld) {
    return fromType(fld.getGenericType());
  }

  public static ClassTree fromClass(Class<?> clazz) {
    return fromType(clazz);
  }

  public static ClassTree fromType(Type type) {
    if (type instanceof ParameterizedType parameterizedType) {
      return new ClassTree(
        (Class<?>) parameterizedType.getRawType(),
        Arrays.stream(parameterizedType.getActualTypeArguments())
          .map(ClassTree::fromType)
          .toArray(ClassTree[]::new)
      );
    }
    return new ClassTree((Class<?>) type, NO_TYPES);
  }
}
