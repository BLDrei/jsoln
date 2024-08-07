package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveByteDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveDoubleDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveFloatDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveIntDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveLongDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.primitive.PrimitiveShortDto;
import org.junit.jupiter.api.Test;

class PrimitivesNotAllowedTest extends AbstractTest {

  @Test
  void deserialize_toPrimitiveNumericTypes_shouldThrowException() {
    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize("{\"_int\":3}", PrimitiveIntDto.class),
      "Unsupported field type: int");

    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize("{\"_byte\":3}", PrimitiveByteDto.class),
      "Unsupported field type: byte");

    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize("{\"" +
        "_short\":3}", PrimitiveShortDto.class),
      "Unsupported field type: short");

    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize("{\"_long\":3}", PrimitiveLongDto.class),
      "Unsupported field type: long");

    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize("{\"_float\":3}", PrimitiveFloatDto.class),
      "Unsupported field type: float");

    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize("{\"_double\":3}", PrimitiveDoubleDto.class),
      "Unsupported field type: double");
  }
}
