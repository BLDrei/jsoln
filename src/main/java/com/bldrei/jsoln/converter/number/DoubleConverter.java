package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.Configuration;
import org.jetbrains.annotations.NotNull;

public final class DoubleConverter extends NumberConverter<Double> {
  @Override
  public Double javaify(@NotNull String value, @NotNull Configuration conf) {
    return Double.parseDouble(value);
  }
}
