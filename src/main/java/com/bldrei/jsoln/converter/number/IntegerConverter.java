package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class IntegerConverter extends NumberConverter<Integer> {
  @Override
  public Integer javaify(@NotNull String value) {
    return Integer.parseInt(value);
  }
}
