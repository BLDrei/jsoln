package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.Configuration;

import java.time.LocalDate;
import java.util.Optional;

public final class LocalDateConverter extends TextConverter<LocalDate> {

  public LocalDateConverter() {
    super(LocalDate.class);
  }

  @Override
  public LocalDate convert(String value) {
    return Optional.ofNullable(Configuration.dateTimeFormatter)
      .map(formatter -> LocalDate.parse(value, formatter))
      .orElse(LocalDate.parse(value));
  }
}
