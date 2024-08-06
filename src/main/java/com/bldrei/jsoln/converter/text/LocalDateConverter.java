package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.Configuration;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Optional;

public final class LocalDateConverter extends TextConverter<LocalDate> {

  @Override
  public LocalDate stringToObject(@NotNull String value) {
    return Optional.ofNullable(Configuration.dateTimeFormatter)
      .map(formatter -> LocalDate.parse(value, formatter))
      .orElse(LocalDate.parse(value));
  }

  @Override
  public String stringify(@NotNull LocalDate ld) {
    var formatter = Configuration.dateTimeFormatter;
    if (formatter == null) return ld.toString();
    return formatter.format(ld);
  }
}
