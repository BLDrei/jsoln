package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveIntDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveShortDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.IntegerDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.ShortDto;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerParamTest extends AbstractTest {

  @Test
  void deserializeInt_primitiveNOK() {
    var jo = new JsonObject(Map.of("_int", new JsonNumber("55555")));

    assertEquals(Integer.parseInt("55555"), Jsoln.deserialize(jo, IntegerDto.class)._int());

    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize(jo, PrimitiveIntDto.class),
      "Unsupported field type: int");
  }
}
