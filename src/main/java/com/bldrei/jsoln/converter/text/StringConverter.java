package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.converter.AbstractConverter;

public class StringConverter extends AbstractConverter<String> {

  public StringConverter() {
    super(String.class);
  }

  @Override
  public String convert(String value) {
    return value;
  }
}
