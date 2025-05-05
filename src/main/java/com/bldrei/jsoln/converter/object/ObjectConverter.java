package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.configuration.Configuration;
import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public abstract sealed class ObjectConverter<T>
  implements AbstractConverter
  permits RecordConverter, MapConverter {

  public abstract T javaify(@NotNull JsonNode jsonNode,
                            @NotNull ClassTreeWithConverters classTree,
                            @NotNull Configuration conf);

  @SuppressWarnings("unchecked")
  public String stringify(@NotNull Object obj,
                          @NotNull ClassTreeWithConverters classTree,
                          StringBuilder sb,
                          @NotNull Configuration conf) {
    return toJsonModelMutableMap((T) obj, classTree, conf)
      .entrySet()
      .stream()
      .map(e -> "\"" + e.getKey() + "\":" + e.getValue())
      .collect(Collectors.joining(",", "{", "}"));
  }

  protected abstract Map<String, String> toJsonModelMutableMap(@NotNull T flatValue,
                                                               @NotNull ClassTreeWithConverters classTree,
                                                               @NotNull Configuration conf);
}

