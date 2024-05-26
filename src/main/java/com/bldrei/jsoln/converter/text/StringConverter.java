package com.bldrei.jsoln.converter.text;

public final class StringConverter extends TextConverter<String> {

  public StringConverter() {
    super(String.class);
  }

  @Override
  public String convert(String value) {
    return value;
  }
}
