package com.bldrei.jsoln.newstructure.json2java.bool;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.BoolDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BooleanParamTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void happyFlow(boolean bool) {
    var jo = """
      {
        "bool": %s
      }
      """.formatted(bool);

    assertEquals(bool, Jsoln.deserialize(jo, BoolDto.class).bool());
  }

  @Test
  void deserializeBoolean_writtenAsString_NOK() {
    var jo = """
      {
        "bool": "true"
      }
      """;

    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize(jo, BoolDto.class),
      "Cannot convert String to BOOLEAN (java.lang.Boolean)");
  }
}
