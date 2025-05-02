package com.bldrei.jsoln.serialize.simple;

import com.bldrei.jsoln.AbstractTest;
import com.bldrei.jsoln.Jsoln;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DtoWithCollectionsSerializationTest extends AbstractTest {

  private final Jsoln jsoln = new Jsoln();

  @Test
  public void testListSetMapSerialization_emptyCollection_differentCollectionImplementations() {
    Stream.of(
      new CollectionDto(List.of(), Map.of()),
      new CollectionDto(new ArrayList<>(), new HashMap<>()),
      new CollectionDto(Arrays.asList(), new LinkedHashMap<>()),
      new CollectionDto(Stream.of(-333).skip(1).toList(), new ConcurrentHashMap<>())
    ).map(jsoln::serialize).forEach(it -> assertEquals("""
      {"ages":[],"childNames":{}}\
      """, it));

  }

  @Test
  public void testListSerialization() {
    String serialized = jsoln.serialize(new CollectionDto(List.of(2, 4, 17), Map.of("ira", List.of("nona", "mima"))));
    assertEquals("""
      {"ages":[2,4,17],"childNames":{"ira":["nona","mima"]}}\
      """, serialized);
  }
}
