package com.bldrei.jsoln.newstructure.tree2java.object;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.JsonBoolean;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;
import com.bldrei.jsoln.newstructure.dto.singlefield.StringDto;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordTestOptionalEnabled extends AbstractTest {

  private static final JsonObject empty = new JsonObject(new HashMap<>());
  private static final JsonObject withStringNull;
  private static final JsonObject withStringFoo = new JsonObject(Map.of("string", new JsonText("foo")));

  static {
    var emptyMap = new HashMap<String, JsonElement>();
    emptyMap.put("string", null);
    withStringNull = new JsonObject(emptyMap);
  }

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
      () -> Jsoln.deserialize(new JsonObject(Map.of("string", JsonBoolean.TRUE)), StringDto.class),
      "Cannot convert JsonBoolean to TEXT (java.lang.String)");
  }
}
