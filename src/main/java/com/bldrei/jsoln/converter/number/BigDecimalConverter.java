package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.configuration.Configuration;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public final class BigDecimalConverter extends NumberConverter<BigDecimal> {
  @Override
  public BigDecimal javaify(@NotNull String value, @NotNull Configuration conf) {
    return new BigDecimal(value);
  }
}
