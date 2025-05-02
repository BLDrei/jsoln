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
    NestedGenericsDto insane = new Jsoln().deserialize("""
      {"nonsense": null}
      """, NestedGenericsDto.class);

    assertTrue(insane.nonsense().isEmpty());
  }

  @Test
  public void nestedGenerics() {
    NestedGenericsDto insane = new Jsoln().deserialize("""
      {"nonsense": [ {"k1": "v1"}, {"k3": null} ] }
      """, NestedGenericsDto.class);

    assertEquals(2, insane.nonsense().get().size());
    assertEquals(1, insane.nonsense().get().getFirst().size());
    assertEquals("v1", insane.nonsense().get().getFirst().get("k1"));
    assertEquals(1, insane.nonsense().get().getLast().size());
    assertNull(insane.nonsense().get().getLast().get("k3"));
  }
}
