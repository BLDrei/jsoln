package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.jsonmodel.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public final class ListConverter extends ArrayConverter<List<?>> {

  @Override
  protected List<?> streamToObject(@NotNull Stream<?> stream, @NotNull List<@Nullable JsonElement> originalJsonArray) {
    return stream.toList();
  }

  @Override
  protected Stream<?> objectToStream(@NotNull List<?> list) {
    return list.stream();
  }
}
