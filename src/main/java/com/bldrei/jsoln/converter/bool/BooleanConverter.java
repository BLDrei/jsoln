package com.bldrei.jsoln.converter.bool;

import com.bldrei.jsoln.configuration.Configuration;
import com.bldrei.jsoln.converter.AbstractConverter;
import org.jetbrains.annotations.NotNull;

public final class BooleanConverter implements AbstractConverter {

  public String stringify(@NotNull Object flatValue, StringBuilder sb, @NotNull Configuration conf) {
    return switch (flatValue) {
      case Boolean b -> String.valueOf(b);
      default -> throw new IllegalStateException();
    };
  }
}
