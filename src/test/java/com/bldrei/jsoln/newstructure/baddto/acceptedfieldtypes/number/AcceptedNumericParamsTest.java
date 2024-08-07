package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.AcceptedNumericParamsDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AcceptedNumericParamsTest extends AbstractTest {

  @Test
  void deserialize_numericParams_happyFlow() {
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

    assertEquals(.456f, dto._float().get());
    assertEquals(.456, dto._double().get());
    assertEquals((byte) -128, dto._byte().get());
    assertEquals((short) 32767, dto._short().get());
    assertEquals(55555, dto._integer().get());
    assertEquals(123456789L, dto._long().get());
    assertEquals(new BigInteger("123456789012345678901234567890"), dto._bigInteger().get());
    assertEquals(new BigDecimal("1234567890.12345678901234567890"), dto._bigDecimal().get());
  }

  @Test //todo: move away
  void deserialize_decimalNumbersWrittenAsIntegers_isAccepted() {
    AcceptedNumericParamsDto dto = Jsoln.deserialize("""
      {
        "_float": 1,
        "_double": -5,
      }""", AcceptedNumericParamsDto.class);

    assertEquals(1, dto._float().get());
    assertEquals(-5, dto._double().get());
  }

  @Test //todo: move to tree2java
  void deserializeNumericParams_decimalTextToWholeNumber_errorWhen() {
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
