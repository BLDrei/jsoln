package com.bldrei.jsoln.util;

import com.bldrei.jsoln.exception.JsolnException;

import java.lang.reflect.Type;

public class ReflectionUtil {

  public static Class findClass(Type type) {
    try {
      return Class.forName(type.getTypeName());
    } catch (ClassNotFoundException d) {
      throw new JsolnException("Error: class with name '%s' not found".formatted(type.getTypeName()));
    }
  }
}
