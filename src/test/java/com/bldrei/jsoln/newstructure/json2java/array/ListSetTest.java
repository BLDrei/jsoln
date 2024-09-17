package com.bldrei.jsoln.newstructure.json2java.array;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.newstructure.dto.singlefield.array.ListDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.array.SetDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListSetTest extends AbstractTest {

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
  void deserializedSetIsUnmodifiable() {
    var jo = """
      {"set": ["foo", "bar"]}
      """;
    var set = Jsoln.deserialize(jo, SetDto.class).set();

    assertEquals(2, set.size());
    assertTrue(set.contains("foo"));
    assertTrue(set.contains("bar"));

    assertSetIsUnmodifiable(set);
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
  void deserializedEmptySetIsUnmodifiable() {
    var jo = """
      {"set": []}
      """;
    var set = Jsoln.deserialize(jo, SetDto.class).set();

    assertEquals(0, set.size());
    assertSetIsUnmodifiable(set);
//    assertSame(Collections.emptySet(), set); //Collections.emptyXXX is not as unmodifiable as XXX.of() (some attempts to modify don't throw exception)
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
  void deserializedSet_containsNull_collectionIsStillUnmodifiable() {
    var jo = """
      {"set": [null]}
      """;
    var set = Jsoln.deserialize(jo, SetDto.class).set();

    assertEquals(1, set.size());
    assertTrue(set.contains(null));
    assertSetIsUnmodifiable(set);
  }

  @Test
  void deserializeList_jsonArrayContainsDifferentTypesOfJsonElement_NOK() {
    var jo = """
      {"list": ["foo", 12]}
      """;
    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize(jo, ListDto.class),
      "Cannot convert JsonNumber to TEXT (java.lang.String)");
  }

  @Test
  void deserializeSet_jsonArrayContainsDifferentTypesOfJsonElement_NOK() {
    var jo = """
      {"set": ["foo", 12]}
      """;
    shouldThrow(JsolnException.class,
      () -> Jsoln.deserialize(jo, SetDto.class),
      "Cannot convert JsonNumber to TEXT (java.lang.String)");
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

  private void assertSetIsUnmodifiable(Set<String> set) {
    shouldThrow(UnsupportedOperationException.class,
      () -> set.add("bar2"),
      null);
    shouldThrow(UnsupportedOperationException.class,
      () -> set.remove("bar"),
      null);
    shouldThrow(UnsupportedOperationException.class,
      () -> set.remove("I am not even in this collection"),
      null);
    shouldThrow(UnsupportedOperationException.class,
      set::clear,
      null);
  }
}
