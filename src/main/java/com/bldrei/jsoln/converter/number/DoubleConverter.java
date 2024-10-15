package com.bldrei.jsoln.converter.number;

import org.jetbrains.annotations.NotNull;

public final class DoubleConverter extends NumberConverter<Double> {
  @Override
  public Double javaify(@NotNull String value) {
    return Double.parseDouble(value);
  }
}
