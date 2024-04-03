package com.bldrei.jsoln.simplesingleparam;

import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.exception.JsonSyntaxException;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class SingleParamTest {

  @Test
  public void deserialize_happyCase_success() {
    SingleRequiredParamDto dto = Jsoln.deserialize("""
      {"requiredString":"12a "}""", SingleRequiredParamDto.class);

    assertNotNull(dto);
    assertEquals("12a ", dto.getRequiredString());
  }

  @Test
  public void deserialize_spacesBetween_success() {
    SingleRequiredParamDto dto = Jsoln.deserialize("""
      { \t "requiredString" \n:  "12a " \n\t  }""", SingleRequiredParamDto.class);

    assertNotNull(dto);
    assertEquals("12a ", dto.getRequiredString());
  }

  @Test
  public void deserialize_prettyPrinted_success() {
    SingleRequiredParamDto dto = Jsoln.deserialize("""
      {
        "requiredString": "12a "
      }""", SingleRequiredParamDto.class);

    assertNotNull(dto);
    assertEquals("12a ", dto.getRequiredString());
  }

  @Test
  public void deserialize_requiredValueNotPresent_exception() {
    Supplier<SingleRequiredParamDto> s = () -> Jsoln.deserialize("""
      {"someOtherKey":"12a "}""", SingleRequiredParamDto.class);

    assertThrowsExactly(JsolnException.class, s::get, "Value not present, but field requiredString is mandatory");
  }

  @Test
  public void deserialize_invalidSyntax() {
    assertThrowsExactly(JsonSyntaxException.class, () -> Jsoln.deserialize("""
      "requiredString":"12a "\
      """, SingleRequiredParamDto.class), "Valid json must be wrapped into {} or []");
  }
}

