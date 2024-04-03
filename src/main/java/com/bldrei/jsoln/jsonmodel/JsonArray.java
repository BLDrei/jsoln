package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.util.ClassTree;
import jdk.jshell.spi.ExecutionControl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bldrei.jsoln.Jsoln.extractValueFromJsonElement;
import static com.bldrei.jsoln.util.ReflectionUtil.findClass;

public final class JsonArray extends JsonElement {

  private final List<JsonElement> array;

  public JsonArray(List<JsonElement> array) {
    this.array = array;
  }

  public Collection getCollection(ClassTree classTree) {
    Class collectionClass = findClass(classTree.rawType());
    Class actualType = findClass(classTree.genericParameters()[0]);
    Stream stream = array.stream().map(jsonElement -> {
      try {
        return extractValueFromJsonElement(jsonElement, ClassTree.fromType(actualType));
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
      return (Set) stream.collect(Collectors.toUnmodifiableSet());
    }
    throw new IllegalArgumentException("Unexpected collection type: " + collectionClass);
  }
}
