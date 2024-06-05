package com.bldrei.jsoln.converter.text;

import lombok.NonNull;

public final class StringConverter extends TextConverter<String> {

  public StringConverter() {
    super(String.class);
  }

  @Override
  public String stringToObject(String value) {
    return value;
  }

  @Override
  public String stringifyT(@NonNull String s) {
    return s;
  }
}
