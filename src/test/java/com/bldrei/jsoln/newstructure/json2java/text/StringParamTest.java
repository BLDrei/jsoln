package com.bldrei.jsoln.newstructure.json2java.text;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.newstructure.dto.singlefield.StringDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringParamTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(strings = {
    "",
    " ",
    "\t\n",
    "foo",
    " foo ",
    "A very long sentence."
  })
  void deserializeString(String value) {
    var json = """
      {"string":"%s"}
      """.formatted(value);

    assertEquals(value, Jsoln.deserialize(json, StringDto.class).string());
  }

  @Test
  void deserializeUnicodeCharacter() {
    var json = """
      {"string":"\u0344"}
      """;

    assertEquals("̈́", Jsoln.deserialize(json, StringDto.class).string());
  }

  @Test
  void deserializeQuote() {
    var json = """
      {"string":"\\""}
      """;

    assertEquals("\"", Jsoln.deserialize(json, StringDto.class).string());
  }

}
