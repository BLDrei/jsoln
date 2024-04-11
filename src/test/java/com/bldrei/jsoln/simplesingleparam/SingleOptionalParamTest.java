package com.bldrei.jsoln.simplesingleparam;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsonSyntaxException;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SingleOptionalParamTest extends AbstractTest {

  @Test
  public void deserialize_happyCase_success() {
    SingleOptionalParamDto dto = Jsoln.deserialize("""
      {"optionalString":"12a "}""", SingleOptionalParamDto.class);

    assertNotNull(dto);
    assertEquals("12a ", dto.getOptionalString().get());
  }

  @Test
  public void deserialize_spacesBetween_success() {
    SingleOptionalParamDto dto = Jsoln.deserialize("""
      { \t "optionalString" \n:  "12a " \n\t  }""", SingleOptionalParamDto.class);

    assertNotNull(dto);
    assertEquals("12a ", dto.getOptionalString().get());
  }

  @Test
  public void deserialize_prettyPrinted_success() {
    SingleOptionalParamDto dto = Jsoln.deserialize("""
      {
        "optionalString": ""
      }""", SingleOptionalParamDto.class);

    assertNotNull(dto);
    assertEquals("", dto.getOptionalString().get());
  }

  @Test
  public void deserialize_requiredValueNotPresent_isOk() {
    SingleOptionalParamDto dto =  Jsoln.deserialize("""
      {}""", SingleOptionalParamDto.class);

    assertTrue(dto.getOptionalString().isEmpty());
  }

  @Test
  public void deserialize_requiredValueIsNul_throwsSyntaxException() {
    shouldThrow(JsonSyntaxException.class,
      () -> Jsoln.deserialize("""
      {"optionalString":nul}""", SingleOptionalParamDto.class),
      "Invalid Json parameter value: 'nul'");
  }

  @Test
  public void deserialize_jsonIsNotWrittenAsObject() {
    Stream.of(
      "",
      "{",
      "}",
      "\"optionalString\":\"12a \"",
      "{\"optionalString\":\"12a \"",
      "\"optionalString\":\"12a \"}"
    ).forEach(json -> shouldThrow(JsonSyntaxException.class,
      () -> Jsoln.deserialize(json, SingleOptionalParamDto.class),
      "Valid json must be wrapped into {} or []")
    );
  }
}
