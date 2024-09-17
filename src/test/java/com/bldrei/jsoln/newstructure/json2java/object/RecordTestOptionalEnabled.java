package com.bldrei.jsoln.newstructure.json2java.object;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.newstructure.dto.singlefield.StringDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordTestOptionalEnabled extends AbstractTest {

  private static final String empty = "{}";
  private static final String withStringNull = "{\"string\": null}";
  private static final String withStringFoo = "{\"string\": \"foo\"}";

  @Test
  void requiredPropertyIsNotPresent_shouldThrowMissingValueException() {
    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize(empty, StringDto.class)
      //, todo: missing value msg
    );
  }

  @Test
  void requiredPropertyIsNull_shouldThrowMissingValueException() {
    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize(withStringNull, StringDto.class)
      //, todo: missing value msg
    );
  }

  @Test
  void requiredPropertyHasValue_OK() {
    var dto = Jsoln.deserialize(withStringFoo, StringDto.class);

    assertEquals("foo", dto.string());
  }

  @Test
  void requiredPropertyHasWrongJsonModelType_NOK() {
    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize("{\"string\": true}", StringDto.class),
      "Cannot convert Boolean to TEXT (java.lang.String)");
  }
}
