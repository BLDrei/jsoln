package com.bldrei.jsoln.converter.array;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public final class SetConverter extends ArrayConverter<Set<?>> {

  @Override
  protected Set<?> streamToObject(@NotNull Stream<?> stream) {
    return streamToSet(stream);
  }

  //copied from Stream::toList, but adapted for Set
  private static Set<?> streamToSet(Stream<?> stream) {
    return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(stream.toArray())));
  }

  @Override
  protected Stream<?> objectToStream(@NotNull Set<?> set) {
    return set.stream();
  }
}
