package com.bldrei.jsoln.newstructure.tree2java.object;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;
import com.bldrei.jsoln.map.EnumAsKeyMapDto;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
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
    var map = new JsonObject(Map.of("localLanguages", new JsonObject(Map.of(
      "EE", new JsonText("EST"),
      "LV", new JsonText("LAT"),
      "LT", new JsonText("LIT")
    ))));
    EnumAsKeyMapDto dtoMap = Jsoln.deserialize(map, EnumAsKeyMapDto.class);

    var localLanguages = dtoMap.localLanguages();
    assertEquals(3, localLanguages.size());

    assertEquals(EST, localLanguages.get(EE));
    assertEquals(LAT, localLanguages.get(LV));
    assertEquals(LIT, localLanguages.get(LT));

    assertMapIsUnmodifiableView(localLanguages, EE, LIT);
  }

  @Test
  public void deserializeMapWithEnumKey_oneKeyMissing_ok() {
    var map = new JsonObject(Map.of("localLanguages", new JsonObject(Map.of(
      "EE", new JsonText("EST"),
      "LV", new JsonText("LAT")
    ))));
    EnumAsKeyMapDto dtoMap = Jsoln.deserialize(map, EnumAsKeyMapDto.class);

    var localLanguages = dtoMap.localLanguages();
    assertEquals(2, localLanguages.size());

    assertEquals(EST, localLanguages.get(EE));
    assertEquals(LAT, localLanguages.get(LV));

    assertMapIsUnmodifiableView(localLanguages, LT, EST);
  }

  @Test
  public void deserializeMapWithEnumKey_empty_ok() {
    var map = new JsonObject(Map.of("localLanguages", new JsonObject(Map.of(
    ))));
    EnumAsKeyMapDto dtoMap = Jsoln.deserialize(map, EnumAsKeyMapDto.class);

    var localLanguages = dtoMap.localLanguages();
    assertEquals(0, localLanguages.size());

    assertMapIsUnmodifiableView(localLanguages, LT, EST);
  }

  @Test
  public void deserializeMapWithEnumKey_nullValues_preserved() {
    var map = new HashMap<String, JsonElement>();
    map.put("EE", null);
    map.put("LV", null);
    map.put("LT", new JsonText("LIT"));
    var jo = new JsonObject(Map.of("localLanguages", new JsonObject(map)));
    EnumAsKeyMapDto dtoMap = Jsoln.deserialize(jo, EnumAsKeyMapDto.class);

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
