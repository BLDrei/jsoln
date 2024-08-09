package com.bldrei.jsoln.newstructure.tree2java.text;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;
import com.bldrei.jsoln.newstructure.dto.singlefield.LocalDateDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.format.DateTimeParseException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalDateTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(strings = {
    "1999-01-01",
    "2020-12-31",
    "2000-06-30",
    "2018-02-28",
    "2020-02-29"
  })
  void deserializeStandardDatePattern_OK(String value) {
    var jo = new JsonObject(Map.of("localDate", new JsonText(value)));

    assertEquals(value, Jsoln.deserialize(jo, LocalDateDto.class).localDate().toString());
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "1999-01-00",
    "1999-01-32",
    "1999-02-30",
    "1999-00-01",
    "1999-13-01",
  })
  void deserializeStandardDatePattern_impossibleDates_NOK(String value) {
    var jo = new JsonObject(Map.of("localDate", new JsonText(value)));

    shouldThrow(DateTimeParseException.class,
      () -> Jsoln.deserialize(jo, LocalDateDto.class));
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "10.05.2003"
  })
  void deserializeDate_notStandardFormat_notImplementedYet(String value) {
    var jo = new JsonObject(Map.of("localDate", new JsonText(value)));

    shouldThrow(DateTimeParseException.class,
      () -> Jsoln.deserialize(jo, LocalDateDto.class));
  }

}
