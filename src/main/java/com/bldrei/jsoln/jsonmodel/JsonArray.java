package com.bldrei.jsoln.jsonmodel;

import jdk.jshell.spi.ExecutionControl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bldrei.jsoln.Jsoln.extractValueFromJsonElement;

public final class JsonArray extends JsonElement {

  private final List<JsonElement> array;

  public JsonArray(List<JsonElement> array) {
    this.array = array;
  }

  public boolean hasNext() {
    return array.get(0) != null;
  }

  public JsonElement next() {
    return array.remove(0);
  }

  public <COLL extends Collection<ITEM>, ITEM> Collection getCollection(Class<COLL> collectionClass) {
    Stream stream = array.stream().map(jsonElement -> {
      try {
        return extractValueFromJsonElement(jsonElement, Class.forName(collectionClass.getTypeName()));
      }
      catch (ExecutionControl.NotImplementedException | NoSuchFieldException | ClassNotFoundException |
             InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
        throw new IllegalArgumentException();
      }
    });

    if (List.class.equals(collectionClass)) {
      return stream.toList();
    }
    if (Set.class.equals(collectionClass)) {
      return (Set<ITEM>) stream.collect(Collectors.toUnmodifiableSet());
    }
    throw new IllegalArgumentException("Unexpected collection type: " + collectionClass);
  }
}
