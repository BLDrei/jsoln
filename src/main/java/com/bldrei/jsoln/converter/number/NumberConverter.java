package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.converter.AbstractConverter;
import org.jetbrains.annotations.NotNull;

public abstract sealed class NumberConverter<N>
  implements AbstractConverter
  permits IntegerConverter, LongConverter, DoubleConverter, BigIntegerConverter, BigDecimalConverter {

  public abstract N javaify(@NotNull String value, @NotNull Configuration conf);

  public String stringify(@NotNull Object value, StringBuilder sb, @NotNull Configuration conf) {
    return String.valueOf((Number) value);
  }
}
