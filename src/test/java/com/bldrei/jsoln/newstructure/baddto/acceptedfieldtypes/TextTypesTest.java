package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.dto.enums.Status;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import jdk.jshell.Snippet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTypesTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(classes = {
    String.class,
    LocalDate.class,
    LocalDateTime.class,
    //todo: LocalTime.class,
    Status.class,
    Snippet.Status.class
  })
  void testOkTypes(Class<?> okType) {
    assertEquals(
      JsonElement.Type.TEXT,
      AcceptedFieldTypes.determineJsonDataType(okType)
    );
  }

  @ParameterizedTest
  @ValueSource(classes = {
    java.util.Date.class,
    java.sql.Date.class,
    Enum.class
  })
  void testNOKTypes(Class<?> nokType) {
    shouldThrow(BadDtoException.class,
      () -> AcceptedFieldTypes.determineJsonDataType(nokType),
      "Unsupported field type: " + nokType.getName()
    );
  }
}
