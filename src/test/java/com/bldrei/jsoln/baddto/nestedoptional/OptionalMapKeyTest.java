package com.bldrei.jsoln.baddto.nestedoptional;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.BadDtoException;
import org.junit.jupiter.api.Test;

class OptionalMapKeyTest extends AbstractTest {

  @Test
  public void optionalMapKey_shouldThrowBadDtoException() {
    shouldThrow(BadDtoException.class, () -> new Jsoln().deserialize("""
      {}
      """, InMapKey.class), "Optional is only allowed as dto field type wrapping layer");
  }

  @Test
  public void optionalMapValue_shouldThrowBadDtoException() {
    shouldThrow(BadDtoException.class, () -> new Jsoln().deserialize("""
      {}
      """, InMapValue.class), "Optional is only allowed as dto field type wrapping layer");
  }

  @Test
  public void optionalListMember_shouldThrowBadDtoException() {
    shouldThrow(BadDtoException.class, () -> new Jsoln().deserialize("""
      {}
      """, InList.class), "Optional is only allowed as dto field type wrapping layer");
  }

  @Test
  public void nestedOptionalMember_shouldThrowBadDtoException() {
    shouldThrow(BadDtoException.class, () -> new Jsoln().deserialize("""
      {}
      """, OptionalOptional.class), "Optional is only allowed as dto field type wrapping layer");
  }

}
