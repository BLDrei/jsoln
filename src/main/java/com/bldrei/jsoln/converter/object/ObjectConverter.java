package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.util.ClassTree;
import lombok.Getter;
import lombok.NonNull;

import java.util.Map;

@Getter
public abstract sealed class ObjectConverter<T> permits RecordConverter, MapConverter {

  private final Class<T> type;

  protected ObjectConverter(Class<T> type) {
    this.type = type;
  }

  public abstract T convert(Map<String, JsonElement> kvMap, ClassTree classTree);

  protected abstract Map<String, JsonElement> toJsonElementMap(@NonNull T flatValue, ClassTree classTree);

  @SuppressWarnings("unchecked")
  public JsonObject objectToJsonObject(@NonNull Object obj, @NonNull ClassTree classTree) {
    return new JsonObject(toJsonElementMap((T) obj, classTree));
  }
}

