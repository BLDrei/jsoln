package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.Configuration;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Optional;

public final class LocalDateTimeConverter extends TextConverter<LocalDateTime> {

  public LocalDateTimeConverter() {
    super(LocalDateTime.class);
  }

  @Override
  public LocalDateTime stringToObject(String value) {
    return Optional.ofNullable(Configuration.dateTimeFormatter)
      .map(formatter -> LocalDateTime.parse(value, formatter))
      .orElse(LocalDateTime.parse(value));
  }

  @Override
  public String stringifyT(@NonNull LocalDateTime ldt) {
    var formatter = Configuration.dateTimeFormatter;
    if (formatter == null) return ldt.toString();
    return formatter.format(ldt);
  }
}
