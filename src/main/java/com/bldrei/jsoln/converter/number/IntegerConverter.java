package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.configuration.Configuration;
import org.jetbrains.annotations.NotNull;

public final class IntegerConverter extends NumberConverter<Integer> {
  @Override
  public Integer javaify(@NotNull String value, @NotNull Configuration conf) {
    return Integer.parseInt(value);
  }
}
