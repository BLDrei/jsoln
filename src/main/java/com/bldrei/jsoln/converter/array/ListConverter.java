package com.bldrei.jsoln.converter.array;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ListConverter extends ArrayConverter<List<?>> {

  @Override
  @UnmodifiableView
  protected List<?> streamToUnmodifiableCollection(@NotNull Stream<?> stream) {
    return Collections.unmodifiableList(stream.collect(Collectors.toList()));
  }

  @Override
  protected Stream<?> collectionToStream(@NotNull List<?> list) {
    return list.stream();
  }
}
