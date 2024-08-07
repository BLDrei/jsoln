package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.converter.number.DoubleConverter;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveDoubleDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveFloatDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.DoubleDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.FloatDto;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DoubleParamTest extends AbstractTest {

  @Test
  void deserializeDouble_primitiveNOK() {
    var jo = new JsonObject(Map.of("_double", new JsonNumber("0.456")));

    assertEquals(Double.parseDouble("0.456"), Jsoln.deserialize(jo, DoubleDto.class)._double());

    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize(jo, PrimitiveDoubleDto.class),
      "Unsupported field type: double");
  }
}
