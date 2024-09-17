package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

public abstract sealed class ObjectConverter<T>
  implements AbstractConverter
  permits RecordConverter, MapConverter {

  public abstract T javaify(@NotNull Map<String, Object> kvMap,
                            @NotNull ClassTreeWithConverters classTree);

  @SuppressWarnings("unchecked")
  public Map<String, Object> toJsonModel(@NotNull Object obj,
                                         @NotNull ClassTreeWithConverters classTree) {
    return Collections.unmodifiableMap(toJsonModelMutableMap((T) obj, classTree));
  }

  protected abstract Map<String, Object> toJsonModelMutableMap(@NotNull T flatValue,
                                                               @NotNull ClassTreeWithConverters classTree);
}

