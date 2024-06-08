package com.bldrei.jsoln.converter.array;

import lombok.NonNull;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SetConverter extends ArrayConverter<Set<?>> {

  @Override
  protected Set<?> streamToObject(@NonNull Stream<?> stream) {
    return stream.collect(Collectors.toUnmodifiableSet());
  }

  @Override
  protected Stream<?> objectToStream(@NonNull Set<?> set) {
    return set.stream();
  }
}
