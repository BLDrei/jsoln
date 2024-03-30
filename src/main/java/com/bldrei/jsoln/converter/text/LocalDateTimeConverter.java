package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.converter.AbstractConverter;

import java.time.LocalDateTime;
import java.util.Optional;

public class LocalDateTimeConverter extends AbstractConverter<LocalDateTime> {

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
