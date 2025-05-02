package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.Configuration;
import org.jetbrains.annotations.NotNull;

public final class LongConverter extends NumberConverter<Long> {
  @Override
  public Long javaify(@NotNull String value, @NotNull Configuration conf) {
    return Long.parseLong(value);
  }
}
