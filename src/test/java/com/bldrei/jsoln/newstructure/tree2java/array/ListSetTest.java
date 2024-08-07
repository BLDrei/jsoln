package com.bldrei.jsoln.newstructure.tree2java.array;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.jsonmodel.JsonArray;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;
import com.bldrei.jsoln.newstructure.dto.singlefield.array.ListDto;
import com.bldrei.jsoln.newstructure.dto.singlefield.array.SetDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListSetTest extends AbstractTest {

  JsonArray oneMember = new JsonArray(List.of(
    new JsonText("foo")
  ));

  @Test
  void deserializedListIsUnmodifiable() {
    var jo = new JsonObject(Map.of("list", oneMember));
    var list = Jsoln.deserialize(jo, ListDto.class).list();

    assertTrue(list.getClass().getName().startsWith("java.util.ImmutableCollections$List"));
    assertEquals(1, list.size());
    assertEquals("foo", list.getFirst());
    shouldThrow(UnsupportedOperationException.class,
      () -> list.add("bar"),
      null);
    shouldThrow(UnsupportedOperationException.class,
      list::removeFirst,
      null);
    shouldThrow(UnsupportedOperationException.class,
      list::clear,
      null);
  }

  @Test
  void deserializedSetIsUnmodifiable() {
    var jo = new JsonObject(Map.of("set", oneMember));
    var set = Jsoln.deserialize(jo, SetDto.class).set();

    assertTrue(set.getClass().getName().startsWith("java.util.ImmutableCollections$Set"));
    assertEquals(1, set.size());
    assertTrue(set.contains("foo"));
    shouldThrow(UnsupportedOperationException.class,
      () -> set.add("bar"),
      null);
    shouldThrow(UnsupportedOperationException.class,
      set::clear,
      null);
  }
}
