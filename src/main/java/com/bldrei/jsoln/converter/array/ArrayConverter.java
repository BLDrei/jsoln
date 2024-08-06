package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.jsonmodel.JsonArray;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.SerializeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public abstract sealed class ArrayConverter<C>
  implements AbstractConverter
  permits ListConverter, SetConverter {

  public C jsonElementsToObject(@NotNull List<JsonElement> array,
                                @NotNull ClassTreeWithConverters classTree) {
    ClassTreeWithConverters actualType = classTree.getGenericParameters()[0];
    Stream<?> stream = array.stream()
      .map(jsonElement -> jsonElement.toObject(actualType));
    return streamToObject(stream);
  }

  @SuppressWarnings("unchecked")
  public JsonArray objectToJsonArray(@NotNull Object collection,
                                     @NotNull ClassTreeWithConverters classTree) {
    ClassTreeWithConverters collectionOfWhat = classTree.getGenericParameters()[0];
    var jsonElements = objectToStream((C) collection)
      .map(it -> SerializeUtil.convertObjectToJsonElement(it, collectionOfWhat))
      .toList();
    return new JsonArray(jsonElements);
  }

  protected abstract C streamToObject(@NotNull Stream<?> stream);

  protected abstract Stream<?> objectToStream(@NotNull C flatValue);

}
