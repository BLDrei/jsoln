package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.Configuration;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;

public final class LocalDateTimeConverter extends TextConverter<LocalDateTime> {

  @Override
  public LocalDateTime stringToObject(@NotNull String value) {
    return Optional.ofNullable(Configuration.dateTimeFormatter)
      .map(formatter -> LocalDateTime.parse(value, formatter))
      .orElse(LocalDateTime.parse(value));
  }

  @Override
  public String stringify(@NotNull LocalDateTime ldt) {
    var formatter = Configuration.dateTimeFormatter;
    if (formatter == null) return ldt.toString();
    return formatter.format(ldt);
  }
}
