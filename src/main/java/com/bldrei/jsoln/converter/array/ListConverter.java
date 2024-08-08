package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public final class ListConverter extends ArrayConverter<List<?>> {

  @Override
  public List<?> jsonElementsToObject(@NotNull List<@Nullable JsonElement> array, @NotNull ClassTreeWithConverters classTree) {
    var collectionMemberType = classTree.getGenericParameters()[0];
    return array
      .stream()
      .map(jsonElement -> {
        if (jsonElement == null) {
          return null;
        }
        if (!jsonElement.canBeConvertedTo(collectionMemberType.getJsonDataType())) {
          throw JsolnException.mmmismatch(collectionMemberType, jsonElement);
        }
        return jsonElement.toObject(collectionMemberType);
      })
      .toList();
  }

  @Override
  protected Stream<?> objectToStream(@NotNull List<?> list) {
    return list.stream();
  }
}
