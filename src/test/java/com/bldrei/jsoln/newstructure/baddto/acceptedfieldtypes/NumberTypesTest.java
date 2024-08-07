package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.cache.RecordDeserializationInfo;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.newstructure.dto.BigDecimalDto;
import com.bldrei.jsoln.newstructure.dto.BigIntegerDto;
import com.bldrei.jsoln.newstructure.dto.primitive.PrimitiveByteDto;
import com.bldrei.jsoln.newstructure.dto.primitive.PrimitiveDoubleDto;
import com.bldrei.jsoln.newstructure.dto.primitive.PrimitiveFloatDto;
import com.bldrei.jsoln.newstructure.dto.primitive.PrimitiveIntDto;
import com.bldrei.jsoln.newstructure.dto.primitive.PrimitiveLongDto;
import com.bldrei.jsoln.newstructure.dto.primitive.PrimitiveShortDto;
import com.bldrei.jsoln.newstructure.dto.wrapper.ByteDto;
import com.bldrei.jsoln.newstructure.dto.wrapper.DoubleDto;
import com.bldrei.jsoln.newstructure.dto.wrapper.FloatDto;
import com.bldrei.jsoln.newstructure.dto.wrapper.IntegerDto;
import com.bldrei.jsoln.newstructure.dto.wrapper.LongDto;
import com.bldrei.jsoln.newstructure.dto.wrapper.NumberDto;
import com.bldrei.jsoln.newstructure.dto.wrapper.ShortDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    ShortDto.class,
    ByteDto.class,
    DoubleDto.class,
    IntegerDto.class,
    LongDto.class,
    FloatDto.class,
    BigIntegerDto.class,
    BigDecimalDto.class
  })
  void dtoWithWrapperTypes_OK(Class<?> dtoClazz) {
    assertDoesNotThrow(() -> RecordDeserializationInfo.from(dtoClazz));
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

  @ParameterizedTest
  @ValueSource(classes = {
    PrimitiveShortDto.class,
    PrimitiveByteDto.class,
    PrimitiveDoubleDto.class,
    PrimitiveIntDto.class,
    PrimitiveLongDto.class,
    PrimitiveFloatDto.class,
    NumberDto.class
  })
  void dtoWithPrimitiveType_NOK(Class<?> dtoClazz) {
    shouldThrow(BadDtoException.class,
      () -> RecordDeserializationInfo.from(dtoClazz),
      "Unsupported field type: " + dtoClazz.getRecordComponents()[0].getType().getName());
  }
}
