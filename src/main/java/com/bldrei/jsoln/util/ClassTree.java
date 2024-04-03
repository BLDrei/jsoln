package com.bldrei.jsoln.util;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public record ClassTree(Type rawType, Type[] genericParameters) {

  public static final Type[] NO_TYPES = new Type[]{};

  public static ClassTree fromField(Field fld) {
    if (fld.getGenericType() instanceof ParameterizedType parameterizedType) {
      return new ClassTree(parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
    }
    return new ClassTree(fld.getType(), NO_TYPES);
  }

  public static ClassTree fromType(Type type) {
    if (type instanceof ParameterizedType parameterizedType) {
      return new ClassTree(parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
    }
    return new ClassTree(type, NO_TYPES);
  }
}
