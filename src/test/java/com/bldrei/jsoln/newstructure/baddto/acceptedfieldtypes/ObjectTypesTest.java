package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.BigIntegerDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.PojoDto;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.BoolDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

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
      JsonElement.Type.OBJECT,
      AcceptedFieldTypes.determineJsonDataType(okType)
    );
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
      () -> AcceptedFieldTypes.determineJsonDataType(nokType),
      "Unsupported field type: " + nokType.getName()
    );
  }
}
