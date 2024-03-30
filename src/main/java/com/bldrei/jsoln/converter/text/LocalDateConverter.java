package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.converter.AbstractConverter;

import java.time.LocalDate;
import java.util.Optional;

public class LocalDateConverter extends AbstractConverter<LocalDate> {

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
