package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.Configuration;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;

public final class LocalDateTimeConverter extends TextConverter<LocalDateTime> {

  @Override
  public LocalDateTime javaify(@NotNull String value, @NotNull Configuration conf) {
    return Optional.ofNullable(conf.getDateTimeFormatter())
      .map(formatter -> LocalDateTime.parse(value, formatter))
      .orElseGet(() -> LocalDateTime.parse(value));
  }

  @Override
  public String stringify(@NotNull LocalDateTime ldt, @NotNull Configuration conf) {
    return Optional.ofNullable(conf.getDateTimeFormatter())
      .map(formatter -> formatter.format(ldt))
      .orElseGet(ldt::toString);
  }
}
