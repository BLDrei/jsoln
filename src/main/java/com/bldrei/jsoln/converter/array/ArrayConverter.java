package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.exception.JsolnException;
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

  public C jsonElementsToObject(@NotNull List<@Nullable JsonElement> array,
                                @NotNull ClassTreeWithConverters classTree) {
    var collectionMemberType = classTree.getGenericParameters()[0];
    var stream = array
      .stream()
      .map(jsonElement -> {
        if (jsonElement == null) {
          return null;
        }
        if (!jsonElement.canBeConvertedTo(collectionMemberType.getJsonDataType())) {
          throw JsolnException.mmmismatch(collectionMemberType, jsonElement);
        }
        return jsonElement.toObject(collectionMemberType);
      });
    return streamToObject(stream, array);
  }

  @SuppressWarnings("unchecked")
  public JsonArray objectToJsonArray(@NotNull Object collection,
                                     @NotNull ClassTreeWithConverters classTree) {
    ClassTreeWithConverters collectionOfWhat = classTree.getGenericParameters()[0];
    List<JsonElement> jsonElements = objectToStream((C) collection)
      .map(it -> SerializeUtil.convertObjectToJsonElement(it, collectionOfWhat))
      .toList();
    return new JsonArray(jsonElements);
  }

  protected abstract C streamToObject(@NotNull Stream<?> stream, @NotNull List<@Nullable JsonElement> originalJsonArray);

  protected abstract Stream<?> objectToStream(@NotNull C flatValue);

}
