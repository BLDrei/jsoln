package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract sealed class ObjectConverter<T>
  implements AbstractConverter
  permits RecordConverter, MapConverter {

  public abstract T jsonElementsMapToObject(@NotNull Map<String, JsonElement> kvMap,
                                            @NotNull ClassTreeWithConverters classTree);

  @SuppressWarnings("unchecked")
  public JsonObject objectToJsonObject(@NotNull Object obj,
                                       @NotNull ClassTreeWithConverters classTree) {
    return new JsonObject(objectToJsonElementsMap((T) obj, classTree));
  }

  protected abstract Map<String, JsonElement> objectToJsonElementsMap(@NotNull T flatValue,
                                                                      @NotNull ClassTreeWithConverters classTree);
}

