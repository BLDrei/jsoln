package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.SerializeUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public abstract sealed class ObjectConverter<T>
  implements AbstractConverter
  permits RecordConverter, MapConverter {

  public abstract T javaify(@NotNull JsonNode jsonNode,
                            @NotNull ClassTreeWithConverters classTree);

  @SuppressWarnings("unchecked")
  public String stringify(@NotNull Object obj,
                          @NotNull ClassTreeWithConverters classTree,
                          StringBuilder sb) {
    return toJsonModelMutableMap((T) obj, classTree)
      .entrySet()
      .stream()
      .map(e -> "\"" + e.getKey() + "\":" + e.getValue())
      .collect(Collectors.joining(",", "{", "}"));
  }

  protected abstract Map<String, String> toJsonModelMutableMap(@NotNull T flatValue,
                                                               @NotNull ClassTreeWithConverters classTree);
}

