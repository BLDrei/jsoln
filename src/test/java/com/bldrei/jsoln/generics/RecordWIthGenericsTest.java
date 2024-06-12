package com.bldrei.jsoln.generics;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.BadDtoException;
import org.junit.jupiter.api.Test;

class RecordWIthGenericsTest extends AbstractTest {

  @Test
  public void recordWithFieldTypeGenericParam() {
    shouldThrow(BadDtoException.class, () -> Jsoln.deserialize("""
      {"applId":12340,"limit":12999.99}\
      """, AmountLeft.class), "Unexpected field type 'T'");
  }

  @Test
  public void recordWithFieldTypeGenericParamArray() {
    shouldThrow(BadDtoException.class, () -> Jsoln.deserialize("""
      {"nums":[]}\
      """, GenericArray.class), "Arrays are not allowed as field types");
  }
}
