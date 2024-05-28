package com.bldrei.jsoln.serialize.simple;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleSerializationTest extends AbstractTest {

  @Test
  public void testSimpleSerialization() {
    String serialized = Jsoln.serialize(new SimpleDto("string", 1, true, null));
    assertEquals("""
      {"s":"string","i":1,"b":true}\
      """, serialized);
  }
}
