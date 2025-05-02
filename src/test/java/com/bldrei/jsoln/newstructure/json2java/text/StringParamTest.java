package com.bldrei.jsoln.newstructure.json2java.text;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.newstructure.dto.singlefield.StringDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringParamTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(strings = {
    "",
    " ",
    "foo",
    " foo ",
    "A very long sentence."
  })
  void deserializeString(String value) {
    var json = """
      {"string":"%s"}
      """.formatted(value);

    assertEquals(value, new Jsoln().deserialize(json, StringDto.class).string());
  }

  @Test
  void deserializeUnicodeCharacter() {
    var json = """
      {"string":"\u0344"}
      """;

    assertEquals("̈́", new Jsoln().deserialize(json, StringDto.class).string());
  }

  @Test
  void deserializeQuote() {
    var json = """
      {"string":"\\""}
      """;

    assertEquals("\"", new Jsoln().deserialize(json, StringDto.class).string());
  }

  @Test
  void deserializeWhitespace() {
    var json = """
      {"string":"\\t\\n"}
      """;

    assertTrue(new Jsoln().deserialize(json, StringDto.class).string().isBlank());
  }


}
