package com.bldrei.jsoln.converter.array;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SetConverter extends ArrayConverter<Set<?>> {

  @Override
  protected Set<?> streamToObject(@NotNull Stream<?> stream) {
    return stream.collect(Collectors.toUnmodifiableSet());
  }

  @Override
  protected Stream<?> objectToStream(@NotNull Set<?> set) {
    return set.stream();
  }
}
