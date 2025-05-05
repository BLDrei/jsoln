package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.configuration.Configuration;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Optional;

public final class LocalDateConverter extends TextConverter<LocalDate> {

  @Override
  public LocalDate javaify(@NotNull String value, @NotNull Configuration conf) {
    return Optional.ofNullable(conf.getDateTimeFormatter())
      .map(formatter -> LocalDate.parse(value, formatter))
      .orElseGet(() -> LocalDate.parse(value));
  }

  @Override
  public String stringify(@NotNull LocalDate ld, @NotNull Configuration conf) {
    return Optional.ofNullable(conf.getDateTimeFormatter())
      .map(formatter -> formatter.format(ld))
      .orElseGet(ld::toString);
  }
}
