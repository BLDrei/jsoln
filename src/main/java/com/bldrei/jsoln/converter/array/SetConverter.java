package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.jsonmodel.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SetConverter extends ArrayConverter<Set<?>> {

  @Override
  protected Set<?> streamToObject(@NotNull Stream<?> stream, @NotNull List<@Nullable JsonElement> originalJsonArray) {
    return originalJsonArray.stream().anyMatch(Objects::isNull)
      ? stream.collect(Collectors.toSet()) //no please
      : stream.collect(Collectors.toUnmodifiableSet());
  }

  @Override
  protected Stream<?> objectToStream(@NotNull Set<?> set) {
    return set.stream();
  }
}
