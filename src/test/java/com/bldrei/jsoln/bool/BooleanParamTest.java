package com.bldrei.jsoln.bool;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanParamTest extends AbstractTest {

  @Test
  public void deserialize_booleanParam_happyFlow() {
    BoolDto dto = Jsoln.deserialize("""
      {"boolPrimitive":  true , "boolWrapper": false}""", BoolDto.class);

    assertTrue(dto.isBoolPrimitive());
    assertFalse(dto.getBoolWrapper()); //todo: should both boolean and Boolean be accepted?
  }

  @Test
  public void deserialize_booleanParam_writtenAsString() {
    shouldThrow(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("""
        {"boolPrimitive":  "true" , "boolWrapper": false}""", BoolDto.class),
      "Not implemented text class: boolean");

    shouldThrow(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("""
        {"boolPrimitive":  true , "boolWrapper": "false"}""", BoolDto.class),
      "Not implemented text class: class java.lang.Boolean");
  }
}
