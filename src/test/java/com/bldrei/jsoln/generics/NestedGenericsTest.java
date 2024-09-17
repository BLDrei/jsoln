package com.bldrei.jsoln.generics;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NestedGenericsTest extends AbstractTest {

  @Test
  public void nestedGenericsDto_empty() {
    NestedGenericsDto insane = Jsoln.deserialize("""
      {"crap": null}
      """, NestedGenericsDto.class);

    assertTrue(insane.crap().isEmpty());
  }

  @Test
  public void nestedGenerics() {
    NestedGenericsDto insane = Jsoln.deserialize("""
      {"crap": [ {"k1": "v1"}, {"k3": null} ] }
      """, NestedGenericsDto.class);

    assertEquals(2, insane.crap().get().size());
    assertEquals(1, insane.crap().get().getFirst().size());
    assertEquals("v1", insane.crap().get().getFirst().get("k1"));
    assertEquals(1, insane.crap().get().getLast().size());
    assertNull(insane.crap().get().getLast().get("k3"));
  }
}
