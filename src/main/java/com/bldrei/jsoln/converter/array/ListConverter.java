package com.bldrei.jsoln.converter.array;

import lombok.NonNull;

import java.util.List;
import java.util.stream.Stream;

public final class ListConverter extends ArrayConverter<List> {
  public ListConverter() {
    super(List.class);
  }

  @Override
  protected List streamToObject(Stream<?> stream) {
    return stream.toList();
  }

  @Override
  protected Stream<?> objectToStream(@NonNull List list) {
    return list.stream();
  }
}
