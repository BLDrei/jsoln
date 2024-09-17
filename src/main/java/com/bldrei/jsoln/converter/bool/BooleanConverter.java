package com.bldrei.jsoln.converter.bool;

import com.bldrei.jsoln.converter.AbstractConverter;
import org.jetbrains.annotations.NotNull;

public final class BooleanConverter implements AbstractConverter {

  public Boolean toJsonModel(@NotNull Object flatValue) {
    return switch (flatValue) {
      case Boolean b -> b;
      default -> throw new IllegalStateException();
    };
  }
}
