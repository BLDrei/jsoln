package com.bldrei.jsoln.converter.text;

import lombok.NonNull;

public final class StringConverter extends TextConverter<String> {

  @Override
  public String stringToObject(@NonNull String value) {
    return value;
  }

  @Override
  public String stringify(@NonNull String s) {
    return s;
  }
}
