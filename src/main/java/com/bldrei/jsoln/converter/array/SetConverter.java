package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.converter.ArrayConverter;
import lombok.NonNull;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetConverter extends ArrayConverter<Set> {
  public SetConverter() {
    super(Set.class);
  }

  @Override
  public Set convert(Stream<?> stream) {
    return stream.collect(Collectors.toUnmodifiableSet());
  }

  @Override
  protected String stringifyT(@NonNull Set flatValue) {
    return ""; //todo
  }
}
