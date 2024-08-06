package com.bldrei.jsoln.converter.text;

import org.jetbrains.annotations.NotNull;

public final class StringConverter extends TextConverter<String> {

  @Override
  public String stringToObject(@NotNull String value) {
    return value;
  }

  @Override
  public String stringify(@NotNull String s) {
    return s;
  }
}
