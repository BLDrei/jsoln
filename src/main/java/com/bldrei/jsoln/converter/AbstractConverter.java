package com.bldrei.jsoln.converter;

import lombok.Getter;

@Getter
public abstract class AbstractConverter<T> {

  private final Class<T> type;

  protected AbstractConverter(Class<T> type) {
    this.type = type;
  }

  public abstract T convert(String value);
}
