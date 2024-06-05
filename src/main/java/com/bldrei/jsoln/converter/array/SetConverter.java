package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.converter.ArrayConverter;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTree;
import com.bldrei.jsoln.util.SerializeUtil;
import lombok.NonNull;

import java.util.List;
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
  protected Stream<?> toStream(@NonNull Set set) {
    return set.stream();
  }
}
