package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.bool;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.JsonBoolean;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveBooleanDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.BoolDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

class BooleanParamTest extends AbstractTest {

  @Test
  void deserializeBoolean_primitiveNOK() {
    var jo = new JsonObject(Map.of("bool", JsonBoolean.FALSE));

    assertFalse(Jsoln.deserialize(jo, BoolDto.class).bool());

    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize(jo, PrimitiveBooleanDto.class),
      "Unsupported field type: boolean");
  }

  @Test
  @Disabled
  void deserialize_booleanParam_writtenAsString() { //todo: move to tree2java tests or remove
    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize("""
        {"boolPrimitive":  "true" , "bool": false}""", BoolDto.class),
      "For field 'boolPrimitive', expected json type is BOOLEAN, but received TEXT");

    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize("""
        {"boolPrimitive":  true , "bool": "false"}""", BoolDto.class),
      "For field 'bool', expected json type is BOOLEAN, but received TEXT");
  }
}
