package com.bldrei.jsoln.util;

import com.bldrei.jsoln.exception.JsolnException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReflectionUtil {

  public static List<Class> getActualTypesFromGenericField(Field genericField) {
    if (genericField.getGenericType() instanceof ParameterizedType parameterizedType) {
      return Arrays.stream(parameterizedType.getActualTypeArguments())
        .map(ReflectionUtil::findClass)
        .toList();
    }
    return Collections.emptyList();
  }

  private static Class findClass(Type type) {
    try {
      return Class.forName(type.getTypeName());
    } catch (ClassNotFoundException d) {
      throw new JsolnException("Error: class with name '%s' not found".formatted(type.getTypeName()));
    }
  }
}
