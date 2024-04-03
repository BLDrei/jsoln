package com.bldrei.jsoln.number.primitive;

import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class PrimitivesNotAllowedTest {

  @Test
  public void deserialize_toPrimitiveNumericTypes_shouldThrowException() {
    assertThrowsExactly(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("{\"primitiveInt\":3}", PrimitiveIntDto.class),
      "Not implemented numeric class: class java.lang.int");

    assertThrowsExactly(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("{\"primitiveByte\":3}", PrimitiveByteDto.class),
      "Not implemented numeric class: class java.lang.byte");

    assertThrowsExactly(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("{\"primitiveLong\":3}", PrimitiveLongDto.class),
      "Not implemented numeric class: class java.lang.long");

    assertThrowsExactly(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("{\"primitiveFloat\":3}", PrimitiveFloatDto.class),
      "Not implemented numeric class: class java.lang.float");

    assertThrowsExactly(UnsupportedOperationException.class,
      () -> Jsoln.deserialize("{\"primitiveDouble\":3}", PrimitiveDoubleDto.class),
      "Not implemented numeric class: class java.lang.double");
  }
}
