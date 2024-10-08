package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.JsonArray;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.SerializeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.List;
import java.util.stream.Stream;

public abstract sealed class ArrayConverter<C>
  implements AbstractConverter
  permits ListConverter, SetConverter {

  public @UnmodifiableView C jsonArrayToUnmodifiableCollection(@NotNull List<@Nullable JsonElement> array,
                                                               @NotNull ClassTreeWithConverters classTree) {
    var collectionMemberType = classTree.getGenericParameters()[0];
    var stream = array
      .stream()
      .map(jsonElement -> {
        if (jsonElement == null) {
          return null;
        }
        if (!jsonElement.canBeConvertedTo(collectionMemberType.getJsonDataType())) { //todo: move to JsonElement
          throw JsolnException.cannotCovertJsonElementToType(collectionMemberType, jsonElement);
        }
        return jsonElement.toObject(collectionMemberType);
      });
    return streamToUnmodifiableCollection(stream);
  }

  @SuppressWarnings("unchecked")
  public JsonArray collectionToJsonArray(@NotNull Object collection,
                                         @NotNull ClassTreeWithConverters classTree) {
    ClassTreeWithConverters collectionOfWhat = classTree.getGenericParameters()[0];
    List<JsonElement> jsonElements = collectionToStream((C) collection)
      .map(it -> SerializeUtil.convertObjectToJsonElement(it, collectionOfWhat))
      .toList();
    return new JsonArray(jsonElements);
  }

  protected abstract @UnmodifiableView C streamToUnmodifiableCollection(@NotNull Stream<?> stream);

  protected abstract Stream<?> collectionToStream(@NotNull C flatValue);

}
