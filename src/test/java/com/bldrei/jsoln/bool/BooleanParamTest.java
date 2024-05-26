package com.bldrei.jsoln.bool;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BooleanParamTest extends AbstractTest {

  @Test
  void deserialize_booleanParam_happyFlow() {
    BoolDto dto = Jsoln.deserialize("""
      {"boolPrimitive":  true , "boolWrapper": false}""", BoolDto.class);

    assertTrue(dto.boolPrimitive());
    assertFalse(dto.boolWrapper()); //todo: should both boolean and Boolean be accepted?
  }

  @Test
  void deserialize_booleanParam_writtenAsString() {
    shouldThrow(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("""
        {"boolPrimitive":  "true" , "boolWrapper": false}""", BoolDto.class),
      "Cannot deserialize text '\"true\"' to: boolean");

    shouldThrow(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("""
        {"boolPrimitive":  true , "boolWrapper": "false"}""", BoolDto.class),
      "Cannot deserialize text '\"false\"' to: class java.lang.Boolean");
  }
}
