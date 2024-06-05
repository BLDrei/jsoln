package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.Configuration;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Optional;

public final class LocalDateConverter extends TextConverter<LocalDate> {

  public LocalDateConverter() {
    super(LocalDate.class);
  }

  @Override
  public LocalDate stringToObject(String value) {
    return Optional.ofNullable(Configuration.dateTimeFormatter)
      .map(formatter -> LocalDate.parse(value, formatter))
      .orElse(LocalDate.parse(value));
  }

  @Override
  public String stringifyT(@NonNull LocalDate ld) {
    var formatter = Configuration.dateTimeFormatter;
    if (formatter == null) return ld.toString();
    return formatter.format(ld);
  }
}
