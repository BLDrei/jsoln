package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.converter.ArrayConverter;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Stream;

public class ListConverter extends ArrayConverter<List> {
  public ListConverter() {
    super(List.class);
  }

  @Override
  public List convert(Stream<?> stream) {
    return stream.toList();
  }

  @Override
  protected Stream<?> toStream(@NonNull List list) {
    return list.stream();
  }
}
