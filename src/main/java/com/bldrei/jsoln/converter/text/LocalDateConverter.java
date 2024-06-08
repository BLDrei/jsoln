package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.Configuration;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Optional;

public final class LocalDateConverter extends TextConverter<LocalDate> {

  @Override
  public LocalDate stringToObject(@NonNull String value) {
    return Optional.ofNullable(Configuration.dateTimeFormatter)
      .map(formatter -> LocalDate.parse(value, formatter))
      .orElse(LocalDate.parse(value));
  }

  @Override
  public String stringify(@NonNull LocalDate ld) {
    var formatter = Configuration.dateTimeFormatter;
    if (formatter == null) return ld.toString();
    return formatter.format(ld);
  }
}
