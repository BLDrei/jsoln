package com.bldrei.jsoln.newstructure.tree2java.text;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;
import com.bldrei.jsoln.newstructure.dto.singlefield.StringDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringParamTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(strings = {
    "",
    " ",
    "\t\n",
    "foo",
    " foo ",
    "\\u034",
    "\"",
    "A very long sentence."
  })
  void deserializeString(String value) {
    var jo = new JsonObject(Map.of("string", new JsonText(value)));

    assertEquals(value, Jsoln.deserialize(jo, StringDto.class).string());
  }

}
