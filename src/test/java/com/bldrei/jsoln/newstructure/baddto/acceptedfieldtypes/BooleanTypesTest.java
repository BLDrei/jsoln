package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.configuration.Configuration;
import com.bldrei.jsoln.cache.RecordDeserializationInfo;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonModelType;
import com.bldrei.jsoln.newstructure.dto.singlefield.primitive.PrimitiveBooleanDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.BoolDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BooleanTypesTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(classes = {
    Boolean.class
  })
  void testOkTypes(Class<?> okType) {
    assertEquals(
      JsonModelType.BOOLEAN,
      AcceptedFieldTypes.determineFieldJsonDataType(okType)
    );
  }

  @ParameterizedTest
  @ValueSource(classes = {
    BoolDto.class
  })
  void dtoWithWrapperTypes_OK(Class<?> dtoClazz) {
    assertDoesNotThrow(() -> RecordDeserializationInfo.from(dtoClazz, Configuration.defaultConf()));
  }

  @ParameterizedTest
  @ValueSource(classes = {
    boolean.class,
    AtomicBoolean.class
  })
  void testNOKTypes(Class<?> nokType) {
    shouldThrow(BadDtoException.class,
      () -> AcceptedFieldTypes.determineFieldJsonDataType(nokType),
      "Unsupported field type: " + nokType.getName()
    );
  }

  @ParameterizedTest
  @ValueSource(classes = {
    PrimitiveBooleanDto.class
  })
  void dtoWithPrimitiveType_NOK(Class<?> dtoClazz) {
    shouldThrow(BadDtoException.class,
      () -> RecordDeserializationInfo.from(dtoClazz, Configuration.defaultConf()),
      "Unsupported field type: " + dtoClazz.getRecordComponents()[0].getType().getName());
  }
}
