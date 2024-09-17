package com.bldrei.jsoln.map;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class MapStringStringTest extends AbstractTest {

  @Test
  void deserializeSimpleMap() {
    DtoWithStringStringMap dtoMap = Jsoln.deserialize("""
      {"map": {"k1": "v1", "k2": "v2", "k3": null} }
      """, DtoWithStringStringMap.class);

    var map = dtoMap.map();
    assertNotNull(map);
    assertEquals(3, map.size());
    assertEquals("v1", map.get("k1"));
    assertEquals("v2", map.get("k2"));
    assertNull(map.get("K1"));
    assertNull(map.get("k3"));
    shouldThrow(UnsupportedOperationException.class, map::clear, null);
    shouldThrow(UnsupportedOperationException.class, () -> map.put("k4", "v4"), null);
    shouldThrow(UnsupportedOperationException.class, () -> map.remove("k2"), null);
  }
}
