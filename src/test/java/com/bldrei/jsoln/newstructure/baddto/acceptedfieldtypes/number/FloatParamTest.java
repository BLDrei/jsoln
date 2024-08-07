package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveFloatDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveLongDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.FloatDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.LongDto;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FloatParamTest extends AbstractTest {

  @Test
  void deserializeFloat_primitiveNOK() {
    var jo = new JsonObject(Map.of("_float", new JsonNumber("0.456")));

    assertEquals(Float.parseFloat("0.456"), Jsoln.deserialize(jo, FloatDto.class)._float());

    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize(jo, PrimitiveFloatDto.class),
      "Unsupported field type: float");
  }
}
