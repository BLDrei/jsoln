package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class DoubleConverter extends NumberConverter<Double> {

  @Override
  public Double stringToObject(@NotNull String value) {
    return Double.valueOf(value);
  }

  protected String stringify(@NotNull Double flatValue) {
    return Double.toString(flatValue);
  }
}
