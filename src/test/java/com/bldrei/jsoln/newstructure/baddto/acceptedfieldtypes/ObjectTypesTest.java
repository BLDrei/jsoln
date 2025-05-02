package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.cache.RecordDeserializationInfo;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonModelType;
import com.bldrei.jsoln.newstructure.dto.singlefield.BigIntegerDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.PojoDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.BoolDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectTypesTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(classes = {
    Map.class,
    BoolDto.class,
    BigIntegerDto.class
  })
  void testOkTypes(Class<?> okType) {
    assertEquals(
      JsonModelType.OBJECT,
      AcceptedFieldTypes.determineFieldJsonDataType(okType)
    );

    if (okType.isRecord()) {
      assertDoesNotThrow(() -> RecordDeserializationInfo.from(okType, Configuration.defaultConf()));
    }
  }

  @ParameterizedTest
  @ValueSource(classes = {
    LinkedHashMap.class,
    TreeMap.class,
    Object.class,
    Record.class,
    PojoDto.class
  })
  void testNOKTypes(Class<?> nokType) {
    shouldThrow(BadDtoException.class,
      () -> AcceptedFieldTypes.determineFieldJsonDataType(nokType),
      "Unsupported field type: " + nokType.getName()
    );
  }
}
