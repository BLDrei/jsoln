package com.bldrei.jsoln.map;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class NestedMapDtoTest extends AbstractTest {

  @Test
  void deserializeSimpleMap() {
    NestedMapDto dtoMap = Jsoln.deserialize("""
      {
        "translationsByLanguage": {
          "EST": {
            "age": "Vanus"
          },
          "ENG": {
            "age": "Age"
          }
        }
      }
      """, NestedMapDto.class);

    var byLang = dtoMap.translationsByLanguage();
    assertEquals(2, byLang.size());

    var est = byLang.get("EST");
    assertEquals(1, est.size());
    assertEquals("Vanus", est.get("age"));

    var eng = byLang.get("ENG");
    assertEquals(1, eng.size());
    assertEquals("Age", eng.get("age"));

  }
}
