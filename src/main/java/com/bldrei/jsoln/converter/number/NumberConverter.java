package com.bldrei.jsoln.converter.number;

import com.bldrei.jsoln.converter.AbstractConverter;
import jakarta.json.JsonNumber;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract sealed class NumberConverter<N>
  implements AbstractConverter
  permits IntegerConverter, LongConverter, DoubleConverter, BigIntegerConverter, BigDecimalConverter {

  public abstract N javaify(@NotNull String value);

  public String stringify(@NotNull Object value, StringBuilder sb) {
    return String.valueOf((Number) value);
  }
}
