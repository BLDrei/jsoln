package com.bldrei.jsoln.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AcceptedNumericParamsTest extends AbstractTest {

  @Test
  public void deserialize_numericParams_happyFlow() {
    AcceptedNumericParamsDto dto = Jsoln.deserialize("""
      {
        "_float": 0.456,
        "_double": 0.456,
        "_byte": -128,
        "_short": 32767,
        "_integer": 55555,
        "_long": 123456789,
        "_bigInteger": 123456789012345678901234567890,
        "_bigDecimal": 1234567890.12345678901234567890
      }""", AcceptedNumericParamsDto.class);

    assertEquals(.456f, dto.get_float().get());
    assertEquals(.456, dto.get_double().get());
    assertEquals((byte) -128, dto.get_byte().get());
    assertEquals((short) 32767, dto.get_short().get());
    assertEquals(55555, dto.get_integer().get());
    assertEquals(123456789L, dto.get_long().get());
    assertEquals(new BigInteger("123456789012345678901234567890"), dto.get_bigInteger().get());
    assertEquals(new BigDecimal("1234567890.12345678901234567890"), dto.get_bigDecimal().get());
  }

  @Test
  public void deserialize_decimalNumbersWrittenAsWholeNumbers_isAccepted() {
    AcceptedNumericParamsDto dto = Jsoln.deserialize("""
      {
        "_float": 1,
        "_double": -5,
      }""", AcceptedNumericParamsDto.class);

    assertEquals(1, dto.get_float().get());
    assertEquals(-5, dto.get_double().get());
  }

  @Test
  public void deserializeNumericParams_decimalTextToWholeNumber_errorWhen() {
    shouldThrow(NumberFormatException.class,
      () -> Jsoln.deserialize("""
      {
        "_byte": 0.1,
        "_short": 0.1,
        "_integer": 0.1,
        "_long": 0.1
      }""", AcceptedNumericParamsDto.class),
      "For input string: \"0.1\"");
  }
}
