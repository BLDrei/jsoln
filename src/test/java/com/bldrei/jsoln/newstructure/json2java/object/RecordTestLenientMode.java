package com.bldrei.jsoln.newstructure.json2java.object;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.configuration.Configuration;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.configuration.RequiredFieldsDefinitionMode;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.newstructure.dto.singlefield.StringDto;
import com.bldrei.jsoln.simplesingleparam.SingleOptionalParamDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RecordTestLenientMode extends AbstractTest {

  private static final String empty = "{}";
  private static final String withStringNull = "{\"string\": null}";
  private static final String withStringFoo = "{\"string\": \"foo\"}";

  private final Jsoln jsoln = new Jsoln(new Configuration(RequiredFieldsDefinitionMode.LENIENT));

  @Test
  void fieldTypeIsOptional_shouldNotBeAllowed() {
    shouldThrow(BadDtoException.class,
      () -> jsoln.deserialize("{}", SingleOptionalParamDto.class)
    );
  }

  @Test
  void propertyIsNotPresent_shouldBeOk() {
    var dto = jsoln.deserialize(empty, StringDto.class);
    assertNull(dto.string());
  }

  @Test
  void requiredPropertyIsNull_shouldThrowMissingValueException() {
    var dto = jsoln.deserialize(withStringNull, StringDto.class);
    assertNull(dto.string());
  }

  @Test
  void requiredPropertyHasValue_OK() {
    var dto = new Jsoln().deserialize(withStringFoo, StringDto.class);

    assertEquals("foo", dto.string());
  }

  @Test
  void requiredPropertyHasWrongJsonModelType_NOK() {
    shouldThrow(JsolnException.class,
      () -> new Jsoln().deserialize("{\"string\": true}", StringDto.class),
      "Cannot convert Boolean to TEXT (java.lang.String)");
  }
}
