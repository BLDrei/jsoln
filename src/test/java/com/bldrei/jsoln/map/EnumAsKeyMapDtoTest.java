package com.bldrei.jsoln.map;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Test;

import static com.bldrei.jsoln.map.Country.EE;
import static com.bldrei.jsoln.map.Country.LT;
import static com.bldrei.jsoln.map.Country.LV;
import static com.bldrei.jsoln.map.Language.EST;
import static com.bldrei.jsoln.map.Language.LAT;
import static com.bldrei.jsoln.map.Language.LIT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EnumAsKeyMapDtoTest extends AbstractTest {

  @Test
  void deserializeSimpleMap() {
    EnumAsKeyMapDto dtoMap = Jsoln.deserialize("""
      {
        "localLanguages": {
          "EE": "EST",
          "LV": "LAT",
          "LT": "LIT"
        }
      }
      """, EnumAsKeyMapDto.class);

    var localLanguages = dtoMap.localLanguages();
    assertEquals(3, localLanguages.size());

    assertEquals(EST, localLanguages.get(EE));
    assertEquals(LAT, localLanguages.get(LV));
    assertEquals(LIT, localLanguages.get(LT));

  }
}
