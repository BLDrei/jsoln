package com.bldrei.jsoln.newstructure.json2java.text;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnReflectionException;
import com.bldrei.jsoln.exception.MissingValueException;
import com.bldrei.jsoln.newstructure.dto.singlefield.enums.EnumDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnumParamTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(strings = {
    "MALE",
    "FEMALE"
  })
  void deserializeEnum_existingValues(String value) {
    var json = """
      {
        "gender": "%s"
      }
      """.formatted(value);

    assertEquals(value, new Jsoln().deserialize(json, EnumDto.class).gender().name());
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "",
    "MaLE",
    " MALE",
    "MALE "
  })
  void deserializeEnum_irrelevantStrings_NOK(String value) {
    var json = """
      {
        "gender": "%s"
      }
      """.formatted(value);

    var ex = shouldThrow(JsolnReflectionException.class,
      () -> new Jsoln().deserialize(json, EnumDto.class),
      "java.lang.IllegalArgumentException: No enum constant com.bldrei.jsoln.newstructure.dto.singlefield.enums.Gender." + value);
    assertEquals(IllegalArgumentException.class, ex.getCause().getClass());
  }

  @Test
  void deserializeEnum_null_treatedAsMissingvalue() {
    var json = """
      {
        "gender": null
      }
      """;

    shouldThrow(MissingValueException.class,
      () -> new Jsoln().deserialize(json, EnumDto.class),
      "Value not present, but field 'gender' is mandatory on dto class com.bldrei.jsoln.newstructure.dto.singlefield.enums.EnumDto");
  }

}
