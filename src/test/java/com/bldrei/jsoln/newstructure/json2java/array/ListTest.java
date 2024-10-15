package com.bldrei.jsoln.newstructure.json2java.array;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.newstructure.dto.singlefield.array.ListDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ListTest extends AbstractTest {

  @Test
  void deserializedListIsUnmodifiable() {
    var jo = """
      {"list": ["foo", "bar"]}
      """;
    var list = Jsoln.deserialize(jo, ListDto.class).list();

    assertEquals(2, list.size());
    assertEquals("foo", list.getFirst());
    assertEquals("bar", list.getLast());

    assertListIsUnmodifiable(list);
  }

  @Test
  void deserializedEmptyListIsUnmodifiable() {
    var jo = """
      {"list": []}
      """;
    var list = Jsoln.deserialize(jo, ListDto.class).list();

    assertEquals(0, list.size());
    assertListIsUnmodifiable(list);
//    assertSame(Collections.emptyList(), list); //Collections.emptyXXX is not as unmodifiable as XXX.of() (some attempts to modify don't throw exception)
  }

  @Test
  void deserializedList_containsNull_collectionIsStillUnmodifiable() {
    var jo = """
      {"list": [null]}
      """;
    var list = Jsoln.deserialize(jo, ListDto.class).list();

    assertEquals(1, list.size());
    assertNull(list.getFirst());
    assertListIsUnmodifiable(list);
  }

  @Test
  void deserializeList_jsonArrayContainsDifferentTypesOfJsonElement_NOK() {
    var jo = """
      {"list": ["foo", 12]}
      """;
    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize(jo, ListDto.class),
      "Cannot convert Long to TEXT (java.lang.String)");
  }

  private void assertListIsUnmodifiable(List<String> list) {
    shouldThrow(UnsupportedOperationException.class,
      () -> list.add("bar2"),
      null);
    shouldThrow(UnsupportedOperationException.class,
      () -> list.remove("I am not even in this collection"),
      null);
    shouldThrow(UnsupportedOperationException.class,
      list::clear,
      null);
  }
}
