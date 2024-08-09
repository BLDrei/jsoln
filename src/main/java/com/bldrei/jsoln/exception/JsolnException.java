package com.bldrei.jsoln.exception;

import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;

public class JsolnException extends RuntimeException {
  public JsolnException(String message) {
    super(message);
  }

  public static JsolnException cannotCovertJsonElementToType(ClassTreeWithConverters expectedFieldType, JsonElement actual) {
    return new JsolnException("Cannot convert %s to %s (%s)"
      .formatted(actual.getClass().getSimpleName(), expectedFieldType.getJsonDataType(), expectedFieldType.getRawType().getName()));
  }
}
