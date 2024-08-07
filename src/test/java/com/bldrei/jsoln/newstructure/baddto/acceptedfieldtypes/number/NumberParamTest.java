package com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.number;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.newstructure.baddto.acceptedfieldtypes.dto.wrapper.NumberDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NumberParamTest extends AbstractTest {

  @ParameterizedTest
  @ValueSource(strings = {
    "{}",
    "{\"number\":null}",
    "{\"number\":12}"
  })
  void type_Number_notSupported(String json) {
    shouldThrow(BadDtoException.class,
      () -> Jsoln.deserialize(json, NumberDto.class),
      "Unsupported field type: java.lang.Number");
  }

}
