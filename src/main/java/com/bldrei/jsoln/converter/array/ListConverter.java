package com.bldrei.jsoln.converter.array;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public final class ListConverter extends ArrayConverter<List<?>> {

  @Override
  @UnmodifiableView
  protected List<?> arrayToUnmodifiableCollection(@NotNull List<?> array) {
    return Collections.unmodifiableList(array);
  }

  @Override
  protected Stream<?> collectionToStream(@NotNull List<?> list) {
    return list.stream();
  }
}
