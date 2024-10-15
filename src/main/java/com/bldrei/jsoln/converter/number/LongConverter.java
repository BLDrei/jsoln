package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class LongConverter extends NumberConverter<Long> {
  @Override
  public Long javaify(@NotNull String value) {
    return Long.parseLong(value);
  }
}
