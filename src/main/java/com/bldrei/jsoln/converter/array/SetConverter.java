package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SetConverter extends ArrayConverter<Set<?>> {

  @Override
  public Set<?> jsonElementsToObject(@NotNull List<@Nullable JsonElement> array, @NotNull ClassTreeWithConverters classTree) {
    boolean containsNull = array.stream().anyMatch(Objects::isNull);

    var actualType = classTree.getGenericParameters()[0];
    var stream = array
      .stream()
      .map(jsonElement -> jsonElement == null ? null : jsonElement.toObject(actualType));

    return containsNull
      ? stream.collect(Collectors.toSet()) //no please
      : stream.collect(Collectors.toUnmodifiableSet());
  }

  @Override
  protected Stream<?> objectToStream(@NotNull Set<?> set) {
    return set.stream();
  }
}
