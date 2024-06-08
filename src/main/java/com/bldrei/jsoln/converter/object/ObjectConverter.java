package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import lombok.NonNull;

import java.util.Map;

public abstract sealed class ObjectConverter<T>
  implements AbstractConverter
  permits RecordConverter, MapConverter {

  public abstract T jsonElementsMapToObject(@NonNull Map<String, JsonElement> kvMap,
                                            @NonNull ClassTreeWithConverters classTree);

  @SuppressWarnings("unchecked")
  public JsonObject objectToJsonObject(@NonNull Object obj,
                                       @NonNull ClassTreeWithConverters classTree) {
    return new JsonObject(objectToJsonElementsMap((T) obj, classTree));
  }

  protected abstract Map<String, JsonElement> objectToJsonElementsMap(@NonNull T flatValue,
                                                                      @NonNull ClassTreeWithConverters classTree);
}

