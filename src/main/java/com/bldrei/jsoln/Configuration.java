package com.bldrei.jsoln;

import com.bldrei.jsoln.exception.JsolnException;

import java.time.format.DateTimeFormatter;
import java.util.function.BiConsumer;

public class Configuration {
  private Configuration() {}

  public static DateTimeFormatter dateTimeFormatter = null;
  public static BiConsumer<String, Class<?>> missingRequiredValueHandler = (String fldName, Class<?> dto) -> {
    throw new JsolnException("Value not present, but field '%s' is mandatory on dto class %s".formatted(fldName, dto.getName()));
  };
  public static boolean serializeIncludeNull = false;
}
