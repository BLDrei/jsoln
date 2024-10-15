package com.bldrei.jsoln.newstructure.json2java.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.newstructure.dto.singlefield.BigDecimalDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.BigIntegerDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.DoubleDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.IntegerDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.wrapper.LongDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class NumberAsStringToNumberTest extends AbstractTest {

  @Test
  void happyFlowMaxValues() {
    var jo = """
       {
         "_double": %s,
         "_bigDecimal": %s,

         "_int": %s,
         "_long": %s,
         "_bigInteger": %s
       }
      """.formatted(
      String.valueOf(Double.MAX_VALUE),
      "12345123451234512345.99999999999999999999",
      String.valueOf(Integer.MAX_VALUE),
      String.valueOf(Long.MAX_VALUE),
      "12345123451234512345"
    );

    assertEquals("1.7976931348623157E308", Jsoln.deserialize(jo, DoubleDto.class)._double().toString());
    assertEquals(new BigDecimal("12345123451234512345.99999999999999999999"), Jsoln.deserialize(jo, BigDecimalDto.class)._bigDecimal());

    assertEquals(Integer.MAX_VALUE, Jsoln.deserialize(jo, IntegerDto.class)._int());
    assertEquals(Long.MAX_VALUE, Jsoln.deserialize(jo, LongDto.class)._long());
    assertEquals(new BigInteger("12345123451234512345"), Jsoln.deserialize(jo, BigIntegerDto.class)._bigInteger());
  }

  @Test
  void happyFlowMinValues() {
    var jo = """
      {
        "_double": %s,
        "_bigDecimal": %s,

        "_int": %s,
        "_long": %s,
        "_bigInteger": %s
      }""".formatted(
      String.valueOf(Double.MIN_VALUE),
      "-12345123451234512345.99999999999999999999",
      String.valueOf(Integer.MIN_VALUE),
      String.valueOf(Long.MIN_VALUE),
      "-12345123451234512345"
    );

    assertEquals("4.9E-324", Jsoln.deserialize(jo, DoubleDto.class)._double().toString());
    assertEquals(new BigDecimal("-12345123451234512345.99999999999999999999"), Jsoln.deserialize(jo, BigDecimalDto.class)._bigDecimal());

    assertEquals(Integer.MIN_VALUE, Jsoln.deserialize(jo, IntegerDto.class)._int());
    assertEquals(Long.MIN_VALUE, Jsoln.deserialize(jo, LongDto.class)._long());
    assertEquals(new BigInteger("-12345123451234512345"), Jsoln.deserialize(jo, BigIntegerDto.class)._bigInteger());
  }

  @Test
  void decimalNumbersWrittenAsIntegers_ok() {
    var jo = """
      {
        "_double": -5,
        "_bigDecimal": 1234
      }
      """;

    assertEquals(-5, Jsoln.deserialize(jo, DoubleDto.class)._double());
    assertEquals(new BigDecimal(1234), Jsoln.deserialize(jo, BigDecimalDto.class)._bigDecimal());
  }

  @Test
  void deserializeNumericParams_decimalValueToNonDecimalType_error() {
    var jo = """
      {
        "_int": 0.1,
        "_long": 0.0,
        "_bigInteger": 0.0
      }""";

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
