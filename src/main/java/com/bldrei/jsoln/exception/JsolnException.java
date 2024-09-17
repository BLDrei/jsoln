package com.bldrei.jsoln.exception;

import com.bldrei.jsoln.util.ClassTreeWithConverters;

public class JsolnException extends RuntimeException {
  public JsolnException(String message) {
    super(message);
  }

  public static JsolnException cannotCovertJsonElementToType(ClassTreeWithConverters expectedFieldType, Class<?> actual) {
    return new JsolnException("Cannot convert %s to %s (%s)"
      .formatted(actual.getSimpleName(), expectedFieldType.getJsonDataType(), expectedFieldType.getRawType().getName()));
  }
}
