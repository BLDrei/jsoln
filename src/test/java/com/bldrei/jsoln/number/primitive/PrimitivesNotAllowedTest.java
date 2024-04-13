package com.bldrei.jsoln.number.primitive;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Test;

class PrimitivesNotAllowedTest extends AbstractTest {

  @Test
  void deserialize_toPrimitiveNumericTypes_shouldThrowException() {
    shouldThrow(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("{\"primitiveInt\":3}", PrimitiveIntDto.class),
      "Not implemented numeric class: int");

    shouldThrow(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("{\"primitiveByte\":3}", PrimitiveByteDto.class),
      "Not implemented numeric class: byte");

    shouldThrow(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("{\"primitiveShort\":3}", PrimitiveShortDto.class),
      "Not implemented numeric class: short");

    shouldThrow(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("{\"primitiveLong\":3}", PrimitiveLongDto.class),
      "Not implemented numeric class: long");

    shouldThrow(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("{\"primitiveFloat\":3}", PrimitiveFloatDto.class),
      "Not implemented numeric class: float");

    shouldThrow(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("{\"primitiveDouble\":3}", PrimitiveDoubleDto.class),
      "Not implemented numeric class: double");
  }
}
