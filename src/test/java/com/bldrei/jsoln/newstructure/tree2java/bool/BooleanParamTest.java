package com.bldrei.jsoln.newstructure.tree2java.bool;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.JsonBoolean;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;
import com.bldrei.jsoln.newstructure.dto.wrapper.BoolDto;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

class BooleanParamTest extends AbstractTest {

  @Test
  void happyFlow() {
    var jo = new JsonObject(Map.of("bool", JsonBoolean.FALSE));

    assertFalse(Jsoln.deserialize(jo, BoolDto.class).bool());
  }

  @Test
  void deserializeBoolean_writtenAsString_NOK() {
    var jo = new JsonObject(Map.of("bool", new JsonText("true")));

    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize(jo, BoolDto.class),
      "For field 'bool', expected json type is BOOLEAN, but received TEXT");
  }
}
