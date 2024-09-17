package com.bldrei.jsoln.newstructure.json2java.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.newstructure.dto.singlefield.BigDecimalDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.BigIntegerDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.ByteDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.DoubleDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.FloatDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.IntegerDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.LongDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.ShortDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class NumberAsStringToNumberTest extends AbstractTest {

  //todo: weird numbers that contain letters or symbols other than a period

  @Test
  void happyFlowMaxValues() {
    var jo = """
       {
         "_float": %s,
         "_double": %s,
         "_bigDecimal": %s,

         "_byte": %s
         "_short": %s
         "_int": %s,
         "_long": %s,
         "_bigInteger": %s
       }
      """.formatted(
      String.valueOf(Float.MAX_VALUE),
      String.valueOf(Double.MAX_VALUE),
      "12345123451234512345.99999999999999999999",
      String.valueOf(Byte.MAX_VALUE),
      String.valueOf(Short.MAX_VALUE),
      String.valueOf(Integer.MAX_VALUE),
      String.valueOf(Long.MAX_VALUE),
      "12345123451234512345"
    );

    assertEquals("3.4028235E38", Jsoln.deserialize(jo, FloatDto.class)._float().toString());
    assertEquals("1.7976931348623157E308", Jsoln.deserialize(jo, DoubleDto.class)._double().toString());
    assertEquals(new BigDecimal("12345123451234512345.99999999999999999999"), Jsoln.deserialize(jo, BigDecimalDto.class)._bigDecimal());

    assertEquals("127", Jsoln.deserialize(jo, ByteDto.class)._byte().toString());
    assertEquals("32767", Jsoln.deserialize(jo, ShortDto.class)._short().toString());
    assertEquals(Integer.MAX_VALUE, Jsoln.deserialize(jo, IntegerDto.class)._int());
    assertEquals(Long.MAX_VALUE, Jsoln.deserialize(jo, LongDto.class)._long());
    assertEquals(new BigInteger("12345123451234512345"), Jsoln.deserialize(jo, BigIntegerDto.class)._bigInteger());
  }

  @Test
  void happyFlowMinValues() {
    var jo = """
      {
        "_float": %s,
        "_double": %s,
        "_bigDecimal": %s,

        "_byte": %s,
        "_short": %s,
        "_int": %s,
        "_long": %s,
        "_bigInteger": %s
      }""".formatted(
      String.valueOf(Float.MIN_VALUE),
      String.valueOf(Double.MIN_VALUE),
      "-12345123451234512345.99999999999999999999",
      String.valueOf(Byte.MIN_VALUE),
      String.valueOf(Short.MIN_VALUE),
      String.valueOf(Integer.MIN_VALUE),
      String.valueOf(Long.MIN_VALUE),
      "-12345123451234512345"
    );

    assertEquals("1.4E-45", Jsoln.deserialize(jo, FloatDto.class)._float().toString());
    assertEquals("4.9E-324", Jsoln.deserialize(jo, DoubleDto.class)._double().toString());
    assertEquals(new BigDecimal("-12345123451234512345.99999999999999999999"), Jsoln.deserialize(jo, BigDecimalDto.class)._bigDecimal());

    assertEquals("-128", Jsoln.deserialize(jo, ByteDto.class)._byte().toString());
    assertEquals("-32768", Jsoln.deserialize(jo, ShortDto.class)._short().toString());
    assertEquals(Integer.MIN_VALUE, Jsoln.deserialize(jo, IntegerDto.class)._int());
    assertEquals(Long.MIN_VALUE, Jsoln.deserialize(jo, LongDto.class)._long());
    assertEquals(new BigInteger("-12345123451234512345"), Jsoln.deserialize(jo, BigIntegerDto.class)._bigInteger());
  }

  @Test
  void decimalNumbersWrittenAsIntegers_ok() {
    var jo = """
      {
        "_float": 1,
        "_double": -5,
        "_bigDecimal": 1234
      }
      """;

    assertEquals(1f, Jsoln.deserialize(jo, FloatDto.class)._float());
    assertEquals(-5, Jsoln.deserialize(jo, DoubleDto.class)._double());
    assertEquals(new BigDecimal(1234), Jsoln.deserialize(jo, BigDecimalDto.class)._bigDecimal());
  }

  @Test
  void deserializeNumericParams_decimalValueToNonDecimalType_error() {
    var jo = """
      {
        "_byte": 0.1,
        "_short": 0.1,
        "_int": 0.1,
        "_long": 0.0,
        "_bigInteger": 0.0
      }""";

    shouldThrow(NumberFormatException.class,
      () -> Jsoln.deserialize(jo, ByteDto.class),
      "For input string: \"0.1\"");

    shouldThrow(NumberFormatException.class,
      () -> Jsoln.deserialize(jo, ShortDto.class),
      "For input string: \"0.1\"");

    shouldThrow(NumberFormatException.class,
      () -> Jsoln.deserialize(jo, IntegerDto.class),
      "For input string: \"0.1\"");

    shouldThrow(NumberFormatException.class,
      () -> Jsoln.deserialize(jo, LongDto.class),
      "For input string: \"0.0\"");

    shouldThrow(NumberFormatException.class,
      () -> Jsoln.deserialize(jo, BigIntegerDto.class),
      "For input string: \".0\""); //omitted zero is native Java weirdness
  }

  @Test
  void bigDecimalShouldAlwaysPreserveScale() {
    var jo = """
      {
        "_bigDecimal": 1234.000
      }""";

    BigDecimal actualBigDecimal = Jsoln.deserialize(jo, BigDecimalDto.class)._bigDecimal();

    Stream.of(
      new BigDecimal("1234.000"),
      new BigDecimal(1234).setScale(3),
      BigDecimal.valueOf(1234000, 3),
      BigDecimal.valueOf(1234).setScale(3)
    ).forEach(bdWithScaleThree -> {
      assertEquals(bdWithScaleThree, actualBigDecimal);
      assertEquals(0, bdWithScaleThree.compareTo(actualBigDecimal));
    });

    Stream.of(
      new BigDecimal("1234.00"),
      new BigDecimal("1234.0000"),
      new BigDecimal(1234),
      new BigDecimal(1234L),
      new BigDecimal(1234.000),
      BigDecimal.valueOf(1234),
      BigDecimal.valueOf(1234.000)
    ).forEach(bdWithScaleNotThree -> {
      assertNotEquals(bdWithScaleNotThree, actualBigDecimal);
      assertEquals(0, bdWithScaleNotThree.compareTo(actualBigDecimal));
    });
  }
}
