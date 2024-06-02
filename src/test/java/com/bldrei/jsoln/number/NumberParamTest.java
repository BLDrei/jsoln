package com.bldrei.jsoln.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnException;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class NumberParamTest extends AbstractTest {

  @Test
  void deserialize_unsupportedClass_shouldThrowException() {
    Stream.of(
//      "{}", "{\"number\":null}", //todo implement: before deserialization, check if field is supported by Jsoln
      "{\"number\":12}"
    ).forEach(json -> {
        shouldThrow(JsolnException.class,
          () -> Jsoln.deserialize(json, NumberParamDto.class),
          "Unsupported field type: class java.lang.Number");
      }
    );
  }

}
