package com.bldrei.jsoln.util;

import com.bldrei.jsoln.exception.JsolnException;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Optional;

public class ReflectionUtil {

  public static Class findClass(Type type) {
    try {
      return Class.forName(type.getTypeName());
    } catch (ClassNotFoundException d) {
      throw new JsolnException("Error: class with name '%s' not found".formatted(type.getTypeName()));
    }
  }

  public static Optional<Method> findSetter(Class dto, String fldName, Class param) {
    try {
      return Optional.of(dto.getDeclaredMethod("set" + capitalizeFirstLetter(fldName), param));
    }
    catch (NoSuchMethodException e) {
      return Optional.empty();
    }
  }

  private static String capitalizeFirstLetter(String str) {
    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
  }
}
