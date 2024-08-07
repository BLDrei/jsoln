package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveByteDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.ByteDto;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteParamTest extends AbstractTest {

  @Test
  void deserializeByte_primitiveNOK() {
    var jo = new JsonObject(Map.of("_byte", new JsonNumber("-128")));

    assertEquals(Byte.parseByte("-128"), Jsoln.deserialize(jo, ByteDto.class)._byte());

    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize(jo, PrimitiveByteDto.class),
      "Unsupported field type: byte");
  }
}
