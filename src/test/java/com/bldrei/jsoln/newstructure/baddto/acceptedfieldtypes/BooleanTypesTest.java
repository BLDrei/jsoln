package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BooleanTypesTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(classes = {
    Boolean.class
  })
  void testOkTypes(Class<?> okType) {
    assertEquals(
      JsonElement.Type.BOOLEAN,
      AcceptedFieldTypes.determineJsonDataType(okType)
    );
  }

  @ParameterizedTest
  @ValueSource(classes = {
    boolean.class,
    AtomicBoolean.class
  })
  void testNOKTypes(Class<?> nokType) {
    shouldThrow(BadDtoException.class,
      () -> AcceptedFieldTypes.determineJsonDataType(nokType),
      "Unsupported field type: " + nokType.getName()
    );
  }
}
