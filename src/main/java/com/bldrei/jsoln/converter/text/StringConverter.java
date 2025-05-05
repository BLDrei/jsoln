package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.configuration.Configuration;
import org.jetbrains.annotations.NotNull;

public final class StringConverter extends TextConverter<String> {

  @Override
  public String javaify(@NotNull String value, @NotNull Configuration conf) {
    return value;
  }

  @Override
  public String stringify(@NotNull String s, @NotNull Configuration conf) {
    return s;
  }
}
