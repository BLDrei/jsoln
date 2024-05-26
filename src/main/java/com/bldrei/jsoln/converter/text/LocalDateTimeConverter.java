package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.Configuration;

import java.time.LocalDateTime;
import java.util.Optional;

public final class LocalDateTimeConverter extends TextConverter<LocalDateTime> {

  public LocalDateTimeConverter() {
    super(LocalDateTime.class);
  }

  @Override
  public LocalDateTime convert(String value) {
    return Optional.ofNullable(Configuration.dateTimeFormatter)
      .map(formatter -> LocalDateTime.parse(value, formatter))
      .orElse(LocalDateTime.parse(value));
  }
}
