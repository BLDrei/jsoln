package com.bldrei.jsoln.newstructure.tree2java.text;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.exception.JsolnReflectionException;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;
import com.bldrei.jsoln.newstructure.dto.singlefield.enums.EnumDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnumParamTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(strings = {
    "MALE",
    "FEMALE"
  })
  void deserializeEnum_existingValues(String value) {
    var jo = new JsonObject(Map.of("gender", new JsonText(value)));

    assertEquals(value, Jsoln.deserialize(jo, EnumDto.class).gender().name());
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "",
    "MaLE",
    " MALE",
    "MALE "
  })
  void deserializeEnum_irrelevantStrings_NOK(String value) {
    var jo = new JsonObject(Map.of("gender", new JsonText(value)));

    var ex = shouldThrow(JsolnReflectionException.class,
      () -> Jsoln.deserialize(jo, EnumDto.class),
      "java.lang.IllegalArgumentException: No enum constant com.bldrei.jsoln.newstructure.dto.singlefield.enums.Gender." + value);
    assertEquals(IllegalArgumentException.class, ex.getCause().getClass());
  }

  @Test
  void deserializeEnum_null_treatedAsMissingvalue() {
    var map = new HashMap<String, JsonElement>();
    map.put("gender", null);
    var jo = new JsonObject(map);

    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize(jo, EnumDto.class),
      "Value not present, but field 'gender' is mandatory on dto class com.bldrei.jsoln.newstructure.dto.singlefield.enums.EnumDto");
  }

}
