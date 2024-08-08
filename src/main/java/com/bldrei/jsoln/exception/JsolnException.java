package com.bldrei.jsoln.exception;

import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;

public class JsolnException extends RuntimeException {
  public JsolnException(String message) {
    super(message);
  }

  public static JsolnException mmmismatch(ClassTreeWithConverters expectedFieldType, JsonElement actual) {
    return new JsolnException("For type %s, expected json model was %s, but received %s."
      .formatted(expectedFieldType.getRawType(), expectedFieldType.getJsonDataType(), actual.getJsonDataType()));
  }
}
