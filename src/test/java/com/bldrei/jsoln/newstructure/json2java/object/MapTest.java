package com.bldrei.jsoln.newstructure.json2java.object;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.map.EnumAsKeyMapDto;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.bldrei.jsoln.map.Country.EE;
import static com.bldrei.jsoln.map.Country.LT;
import static com.bldrei.jsoln.map.Country.LV;
import static com.bldrei.jsoln.map.Language.EST;
import static com.bldrei.jsoln.map.Language.LAT;
import static com.bldrei.jsoln.map.Language.LIT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MapTest extends AbstractTest {

  @Test
  public void deserializeMapWithEnumKey_allEnumKeysPresent_ok() {
    var json = """
      {
        "localLanguages": {
          "EE": "EST",
          "LV": "LAT",
          "LT": "LIT"
        }
      }
      """;
    EnumAsKeyMapDto dtoMap = new Jsoln().deserialize(json, EnumAsKeyMapDto.class);

    var localLanguages = dtoMap.localLanguages();
    assertEquals(3, localLanguages.size());

    assertEquals(EST, localLanguages.get(EE));
    assertEquals(LAT, localLanguages.get(LV));
    assertEquals(LIT, localLanguages.get(LT));

    assertMapIsUnmodifiableView(localLanguages, EE, LIT);
  }

  @Test
  public void deserializeMapWithEnumKey_oneKeyMissing_ok() {
    var json = """
      {
        "localLanguages": {
          "EE": "EST",
          "LV": "LAT"
        }
      }
      """;
    EnumAsKeyMapDto dtoMap = new Jsoln().deserialize(json, EnumAsKeyMapDto.class);

    var localLanguages = dtoMap.localLanguages();
    assertEquals(2, localLanguages.size());

    assertEquals(EST, localLanguages.get(EE));
    assertEquals(LAT, localLanguages.get(LV));

    assertMapIsUnmodifiableView(localLanguages, LT, EST);
  }

  @Test
  public void deserializeMapWithEnumKey_empty_ok() {
    var json = """
      {
        "localLanguages": {}
      }
      """;
    EnumAsKeyMapDto dtoMap = new Jsoln().deserialize(json, EnumAsKeyMapDto.class);

    var localLanguages = dtoMap.localLanguages();
    assertEquals(0, localLanguages.size());

    assertMapIsUnmodifiableView(localLanguages, LT, EST);
  }

  @Test
  public void deserializeMapWithEnumKey_nullValues_preserved() {
    var json = """
      {
        "localLanguages": {
          "EE": null,
          "LV": null,
          "LT": "LIT"
        }
      }
      """;
    EnumAsKeyMapDto dtoMap = new Jsoln().deserialize(json, EnumAsKeyMapDto.class);

    var localLanguages = dtoMap.localLanguages();
    assertEquals(3, localLanguages.size());

    assertMapIsUnmodifiableView(localLanguages, LT, EST);
  }

  private <K, V> void assertMapIsUnmodifiableView(Map<K, V> map, K exampleKey, V exampleValue) {
    shouldThrow(UnsupportedOperationException.class,
      () -> map.put(exampleKey, exampleValue),
      null);
    shouldThrow(UnsupportedOperationException.class,
      () -> map.remove(exampleKey),
      null);
    shouldThrow(UnsupportedOperationException.class,
      map::clear,
      null);

    assertEquals("UnmodifiableMap", map.getClass().getSimpleName());
  }
}
