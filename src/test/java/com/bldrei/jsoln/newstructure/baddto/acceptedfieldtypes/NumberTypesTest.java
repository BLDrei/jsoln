package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberTypesTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(classes = {
    Integer.class,
    Long.class,
    Double.class,
    Float.class,
    Byte.class,
    Short.class,
    BigInteger.class,
    BigDecimal.class
  })
  void testOkTypes(Class<?> okType) {
    assertEquals(
      JsonElement.Type.NUMBER,
      AcceptedFieldTypes.determineJsonDataType(okType)
    );
  }

  @ParameterizedTest
  @ValueSource(classes = {
    Number.class,
    AtomicInteger.class,
    AtomicLong.class,
    int.class,
    long.class,
    double.class,
    byte.class,
    short.class,
  })
  void testNOKTypes(Class<?> nokType) {
    shouldThrow(BadDtoException.class,
      () -> AcceptedFieldTypes.determineJsonDataType(nokType),
      "Unsupported field type: " + nokType.getName()
    );
  }
}
