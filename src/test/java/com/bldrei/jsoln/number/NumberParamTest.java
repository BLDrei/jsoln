package com.bldrei.jsoln.number;

import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.exception.JsonSyntaxException;
import com.bldrei.jsoln.number.NumberParamDto;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class NumberParamTest {

  @Test
  public void deserialize_unsupportedClass_shouldThrowException() {
    Stream.of(
//      "{}", "{\"number\":null}", //todo implement: before deserialization, check if field is supported by Jsoln
      "{\"number\":12}"
    ).forEach(json -> assertThrowsExactly(UnsupportedOperationException.class,
      () -> Jsoln.deserialize(json, NumberParamDto.class),
      "Not implemented numeric class: class java.lang.Number")
    );
  }
//  @Test
//  public void deserialize_NumberClassIsNotSupported() {
//    NumberParamDto dto = Jsoln.deserialize("""
//      {"number":12}""", NumberParamDto.class);
//  }

//  @Test
//  public void deserialize_happyCase_success() {
//    NumberParamDto dto = Jsoln.deserialize("""
//      {"requiredString":"12a "}""", NumberParamDto.class);
//
//    assertNotNull(dto);
//    assertEquals("12a ", dto.getRequiredString());
//  }
//
//  @Test
//  public void deserialize_spacesBetween_success() {
//    NumberParamDto dto = Jsoln.deserialize("""
//      { \t "requiredString" \n:  "12a " \n\t  }""", NumberParamDto.class);
//
//    assertNotNull(dto);
//    assertEquals("12a ", dto.getRequiredString());
//  }
//
//  @Test
//  public void deserialize_prettyPrinted_success() {
//    NumberParamDto dto = Jsoln.deserialize("""
//      {
//        "requiredString": "12a "
//      }""", NumberParamDto.class);
//
//    assertNotNull(dto);
//    assertEquals("12a ", dto.getRequiredString());
//  }
//
//  @Test
//  public void deserialize_requiredValueNotPresent_exception() {
//    Supplier<NumberParamDto> s = () -> Jsoln.deserialize("""
//      {"someOtherKey":"12a "}""", NumberParamDto.class);
//
//    assertThrowsExactly(JsolnException.class, s::get, "Value not present, but field requiredString is mandatory");
//  }
//
//  @Test
//  public void deserialize_invalidSyntax() {
//    assertThrowsExactly(JsonSyntaxException.class, () -> Jsoln.deserialize("""
//      "requiredString":"12a "\
//      """, NumberParamDto.class), "Valid json must be wrapped into {} or []");
//  }
}
