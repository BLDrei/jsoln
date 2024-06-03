package com.bldrei.jsoln.bool;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnException;
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
    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize("""
        {"boolPrimitive":  "true" , "boolWrapper": false}""", BoolDto.class),
      "For field 'boolPrimitive', expected json type is BOOLEAN, but received TEXT"); //todo: change to wrong type exception (expected JsonBoolean, was JsonText)

    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize("""
        {"boolPrimitive":  true , "boolWrapper": "false"}""", BoolDto.class),
      "For field 'boolWrapper', expected json type is BOOLEAN, but received TEXT");
  }
}
