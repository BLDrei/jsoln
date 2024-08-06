package com.bldrei.jsoln.converter.array;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public final class ListConverter extends ArrayConverter<List<?>> {

  @Override
  protected List<?> streamToObject(@NotNull Stream<?> stream) {
    return stream.toList();
  }

  @Override
  protected Stream<?> objectToStream(@NotNull List<?> list) {
    return list.stream();
  }
}
