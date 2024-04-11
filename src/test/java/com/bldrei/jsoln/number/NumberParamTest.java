package com.bldrei.jsoln.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class NumberParamTest extends AbstractTest {

  @Test
  public void deserialize_unsupportedClass_shouldThrowException() {
    Stream.of(
//      "{}", "{\"number\":null}", //todo implement: before deserialization, check if field is supported by Jsoln
      "{\"number\":12}"
    ).forEach(json -> {
        shouldThrow(UnsupportedOperationException.class,
          () -> Jsoln.deserialize(json, NumberParamDto.class),
          "Not implemented numeric class: class java.lang.Number");
      }
    );
  }

}
