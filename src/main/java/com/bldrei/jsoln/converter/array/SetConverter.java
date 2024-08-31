package com.bldrei.jsoln.converter.array;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SetConverter extends ArrayConverter<Set<?>> {

  @Override
  @UnmodifiableView
  protected Set<?> streamToUnmodifiableCollection(@NotNull Stream<?> stream) {
    return Collections.unmodifiableSet(stream.collect(Collectors.toSet()));
  }

  @Override
  protected Stream<?> collectionToStream(@NotNull Set<?> set) {
    return set.stream();
  }
}
