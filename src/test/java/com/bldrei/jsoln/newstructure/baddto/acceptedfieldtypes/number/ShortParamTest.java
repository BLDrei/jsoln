package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveByteDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveLongDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveShortDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.ByteDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.LongDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.ShortDto;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShortParamTest extends AbstractTest {

  @Test
  void deserializeShort_primitiveNOK() {
    var jo = new JsonObject(Map.of("_short", new JsonNumber("32767")));

    assertEquals(Short.parseShort("32767"), Jsoln.deserialize(jo, ShortDto.class)._short());

    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize(jo, PrimitiveShortDto.class),
      "Unsupported field type: short");
  }
}
