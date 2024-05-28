package com.bldrei.jsoln.serialize.simple;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DtoWithCollectionsSerializationTest extends AbstractTest {


  @Test
  public void testListSetMapSerialization_emptyCollection_differentCollectionImplementations() {
    Stream.of(
      new CollectionDto(List.of(), Set.of(), Map.of()),
      new CollectionDto(new ArrayList<>(), new HashSet<>(), new HashMap<>()),
      new CollectionDto(Arrays.asList(), new TreeSet<>(), new LinkedHashMap<>()),
      new CollectionDto(Stream.of(-333).skip(1).toList(), Stream.of("").skip(1).collect(Collectors.toSet()), new ConcurrentHashMap<>())
    ).map(Jsoln::serialize).forEach(it -> assertEquals("""
      {"ages":[],"names":[],"childNames":{}}\
      """, it));

  }

  @Test
  public void testListSerialization() {
    String serialized = Jsoln.serialize(new CollectionDto(List.of(2, 4, 17), null, null));
    assertEquals("""
      {"ages":[2,4,17]}\
      """, serialized);
  }
}
