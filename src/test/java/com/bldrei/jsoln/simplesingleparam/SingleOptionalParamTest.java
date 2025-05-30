package com.bldrei.jsoln.simplesingleparam;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.fasterxml.jackson.core.JsonParseException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SingleOptionalParamTest extends AbstractTest {

  @Test
  void deserialize_happyCase_success() {
    SingleOptionalParamDto dto = new Jsoln().deserialize("""
      {"optionalString":"12a "}""", SingleOptionalParamDto.class);

    assertNotNull(dto);
    assertEquals("12a ", dto.optionalString().get());
  }

  @Test
  void deserialize_spacesBetween_success() {
    SingleOptionalParamDto dto = new Jsoln().deserialize("""
      { \t "optionalString" \n:  "12a " \n\t  }""", SingleOptionalParamDto.class);

    assertNotNull(dto);
    assertEquals("12a ", dto.optionalString().get());
  }

  @Test
  void deserialize_prettyPrinted_success() {
    SingleOptionalParamDto dto = new Jsoln().deserialize("""
      {
        "optionalString": ""
      }""", SingleOptionalParamDto.class);

    assertNotNull(dto);
    assertEquals("", dto.optionalString().get());
  }

  @Test
  void deserialize_requiredValueNotPresent_isOk() {
    SingleOptionalParamDto dto = new Jsoln().deserialize("""
      {}""", SingleOptionalParamDto.class);

    assertTrue(dto.optionalString().isEmpty());
  }

  @Test
  void deserialize_requiredValueIsNul_throwsSyntaxException() {
    shouldThrow(JsonParseException.class,
      () -> new Jsoln().deserialize("""
        {"optionalString":nul}""", SingleOptionalParamDto.class));
  }

  @Disabled
  @Test
  void deserialize_jsonIsNotWrittenAsObject() {
    Stream.of(
      "{",
      "}",
      "\"optionalString\":\"12a \"",
      "{\"optionalString\":\"12a \"",
      "\"optionalString\":\"12a \"}"
    ).forEach(json -> shouldThrow(IllegalArgumentException.class,
      () -> new Jsoln().deserialize(json, SingleOptionalParamDto.class))
    );
  }
}
