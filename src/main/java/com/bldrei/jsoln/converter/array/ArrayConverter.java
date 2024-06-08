package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.jsonmodel.JsonArray;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.SerializeUtil;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Stream;

@Getter
public abstract sealed class ArrayConverter<C>
  implements AbstractConverter
  permits ListConverter, SetConverter {

  private final Class<C> type;

  protected ArrayConverter(Class<C> type) {
    this.type = type;
  }

  public C jsonElementsToObject(List<JsonElement> array, @NonNull ClassTreeWithConverters classTree) {
    ClassTreeWithConverters actualType = classTree.getGenericParameters()[0];
    Stream<?> stream = array.stream()
      .map(jsonElement -> jsonElement.toObject(actualType));
    return streamToObject(stream);
  }

  @SuppressWarnings("unchecked")
  public JsonArray objectToJsonArray(@NonNull Object collection, @NonNull ClassTreeWithConverters classTree) {
    ClassTreeWithConverters collectionOfWhat = classTree.getGenericParameters()[0];
    var jsonElements = objectToStream((C) collection)
      .map(it -> SerializeUtil.convertObjectToJsonElement(it, collectionOfWhat))
      .toList();
    return new JsonArray(jsonElements);
  }

  protected abstract C streamToObject(Stream<?> stream);

  protected abstract Stream<?> objectToStream(@NonNull C flatValue);

}
