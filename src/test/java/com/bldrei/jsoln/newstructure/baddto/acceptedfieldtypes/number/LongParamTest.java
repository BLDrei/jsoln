package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveLongDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveShortDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.LongDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.ShortDto;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongParamTest extends AbstractTest {

  @Test
  void deserializeLong_primitiveNOK() {
    var jo = new JsonObject(Map.of("_long", new JsonNumber("123456789")));

    assertEquals(Long.parseLong("123456789"), Jsoln.deserialize(jo, LongDto.class)._long());

    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize(jo, PrimitiveLongDto.class),
      "Unsupported field type: long");
  }
}
