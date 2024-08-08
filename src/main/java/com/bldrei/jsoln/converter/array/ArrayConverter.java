package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.jsonmodel.JsonArray;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.SerializeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public abstract sealed class ArrayConverter<C>
  implements AbstractConverter
  permits ListConverter, SetConverter {

  public abstract C jsonElementsToObject(@NotNull List<@Nullable JsonElement> array,
                                         @NotNull ClassTreeWithConverters classTree);

  @SuppressWarnings("unchecked")
  public JsonArray objectToJsonArray(@NotNull Object collection,
                                     @NotNull ClassTreeWithConverters classTree) {
    ClassTreeWithConverters collectionOfWhat = classTree.getGenericParameters()[0];
    List<JsonElement> jsonElements = objectToStream((C) collection)
      .map(it -> SerializeUtil.convertObjectToJsonElement(it, collectionOfWhat))
      .toList();
    return new JsonArray(jsonElements);
  }

//  protected abstract C streamToObject(@NotNull Stream<?> stream);

  protected abstract Stream<?> objectToStream(@NotNull C flatValue);

}
