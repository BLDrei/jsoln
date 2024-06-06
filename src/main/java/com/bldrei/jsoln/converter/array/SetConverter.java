package com.bldrei.jsoln.converter.array;

import lombok.NonNull;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SetConverter extends ArrayConverter<Set> {
  public SetConverter() {
    super(Set.class);
  }

  @Override
  public Set convert(Stream<?> stream) {
    return stream.collect(Collectors.toUnmodifiableSet());
  }

  @Override
  protected Stream<?> toStream(@NonNull Set set) {
    return set.stream();
  }
}
