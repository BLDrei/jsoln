package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.cache.RecordDeserializationInfo;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonModelType;
import com.bldrei.jsoln.newstructure.dto.singlefield.BigDecimalDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.BigIntegerDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.primitive.PrimitiveByteDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.primitive.PrimitiveDoubleDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.primitive.PrimitiveFloatDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.primitive.PrimitiveIntDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.primitive.PrimitiveLongDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.primitive.PrimitiveShortDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.ByteDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.DoubleDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.FloatDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.IntegerDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.LongDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.NumberDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.ShortDto;
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
    BigInteger.class,
    BigDecimal.class
  })
  void testOkTypes(Class<?> okType) {
    assertEquals(
      JsonModelType.NUMBER,
      AcceptedFieldTypes.determineFieldJsonDataType(okType)
    );
  }

  @ParameterizedTest
  @ValueSource(classes = {
    DoubleDto.class,
    IntegerDto.class,
    LongDto.class,
    BigIntegerDto.class,
    BigDecimalDto.class
  })
  void dtoWithWrapperTypes_OK(Class<?> dtoClazz) {
    assertDoesNotThrow(() -> RecordDeserializationInfo.from(dtoClazz, Configuration.defaultConf()));
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

    Float.class,
    Byte.class,
    Short.class,
  })
  void testNOKTypes(Class<?> nokType) {
    shouldThrow(BadDtoException.class,
      () -> AcceptedFieldTypes.determineFieldJsonDataType(nokType),
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
    NumberDto.class,

    ShortDto.class,
    ByteDto.class,
    FloatDto.class
  })
  void dtoWithNOKTypes(Class<?> dtoClazz) {
    shouldThrow(BadDtoException.class,
      () -> RecordDeserializationInfo.from(dtoClazz, Configuration.defaultConf()),
      "Unsupported field type: " + dtoClazz.getRecordComponents()[0].getType().getName());
  }
}
