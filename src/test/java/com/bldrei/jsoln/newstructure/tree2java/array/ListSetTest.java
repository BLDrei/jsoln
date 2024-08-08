package com.bldrei.jsoln.newstructure.tree2java.array;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.jsonmodel.JsonArray;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;
import com.bldrei.jsoln.newstructure.dto.singlefield.array.ListDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.array.SetDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListSetTest extends AbstractTest {

  private static final JsonArray fooBar = new JsonArray(List.of(
    new JsonText("foo"), new JsonText("bar")
  ));
  private static final JsonArray empty = new JsonArray(new ArrayList<>());
  private static final JsonArray withNull;

  static {
    List<JsonElement> withNullList = new ArrayList<>();
    withNullList.add(null);
    withNull = new JsonArray(withNullList);
  }

  @Test
  void deserializedListIsUnmodifiable() {
    var jo = new JsonObject(Map.of("list", fooBar));
    var list = Jsoln.deserialize(jo, ListDto.class).list();

    assertEquals(2, list.size());
    assertEquals("foo", list.getFirst());
    assertEquals("bar", list.getLast());

    assertListIsUnmodifiable(list);
  }

  @Test
  void deserializedSetIsUnmodifiable() {
    var jo = new JsonObject(Map.of("set", fooBar));
    var set = Jsoln.deserialize(jo, SetDto.class).set();

    assertEquals(2, set.size());
    assertTrue(set.contains("foo"));
    assertTrue(set.contains("bar"));

    assertSetIsUnmodifiable(set);
  }

  @Test
  void deserializedEmptyListIsUnmodifiable() {
    var jo = new JsonObject(Map.of("list", empty));
    var list = Jsoln.deserialize(jo, ListDto.class).list();

    assertEquals(0, list.size());
    assertListIsUnmodifiable(list);
//    assertSame(Collections.emptyList(), list); //Collections.emptyXXX is not as unmodifiable as XXX.of() (some attempts to modify don't throw exception)
  }

  @Test
  void deserializedEmptySetIsUnmodifiable() {
    var jo = new JsonObject(Map.of("set", empty));
    var set = Jsoln.deserialize(jo, SetDto.class).set();

    assertEquals(0, set.size());
    assertSetIsUnmodifiable(set);
//    assertSame(Collections.emptySet(), set); //Collections.emptyXXX is not as unmodifiable as XXX.of() (some attempts to modify don't throw exception)
  }

  @Test
  void deserializedList_nullsStayInPlace() {
    var jo = new JsonObject(Map.of("list", withNull));
    var list = Jsoln.deserialize(jo, ListDto.class).list();

    assertEquals(1, list.size());
    assertNull(list.getFirst());
    assertListIsUnmodifiable(list);
  }

  @Test
  void deserializedSet_containsNull_setIsModifiable() {
    var jo = new JsonObject(Map.of("set", withNull));
    var set = Jsoln.deserialize(jo, SetDto.class).set();

    assertEquals(1, set.size());
    assertTrue(set.contains(null));
    assertDoesNotThrow(() -> set.add("foo"));
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
