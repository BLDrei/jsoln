package com.bldrei.jsoln.number;

import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.exception.JsonSyntaxException;
import com.bldrei.jsoln.number.NumberParamDto;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.bldrei.jsoln.utils.TestUtil.shouldThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class NumberParamTest {

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
