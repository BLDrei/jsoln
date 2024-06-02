package com.bldrei.jsoln.number.primitive;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnException;
import org.junit.jupiter.api.Test;

class PrimitivesNotAllowedTest extends AbstractTest {

  @Test
  void deserialize_toPrimitiveNumericTypes_shouldThrowException() {
    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize("{\"primitiveInt\":3}", PrimitiveIntDto.class),
      "Unsupported field type: int");

    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize("{\"primitiveByte\":3}", PrimitiveByteDto.class),
      "Unsupported field type: byte");

    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize("{\"primitiveShort\":3}", PrimitiveShortDto.class),
      "Unsupported field type: short");

    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize("{\"primitiveLong\":3}", PrimitiveLongDto.class),
      "Unsupported field type: long");

    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize("{\"primitiveFloat\":3}", PrimitiveFloatDto.class),
      "Unsupported field type: float");

    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize("{\"primitiveDouble\":3}", PrimitiveDoubleDto.class),
      "Unsupported field type: double");
  }
}
