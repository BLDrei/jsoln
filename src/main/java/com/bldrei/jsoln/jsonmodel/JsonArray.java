package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.util.ReflectionUtil;
import jdk.jshell.spi.ExecutionControl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
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

  public <COLL extends Collection<ITEM>, ITEM> Collection getCollection(Class<COLL> collectionClass, Class<ITEM> actualType) {
    Stream stream = array.stream().map(jsonElement -> {
      try {
        return extractValueFromJsonElement(jsonElement, actualType, Collections.emptyList() /*actually wont work if List<List<>>, so consider restricting such stuff*/);
      }
      catch (ExecutionControl.NotImplementedException | NoSuchFieldException |
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
