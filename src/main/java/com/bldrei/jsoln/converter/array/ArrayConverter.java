package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonModelType;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.DeserializeUtil;
import com.bldrei.jsoln.util.SerializeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.List;
import java.util.stream.Stream;

public abstract sealed class ArrayConverter<C>
  implements AbstractConverter
  permits ListConverter, SetConverter {

  public @UnmodifiableView C javaify(@NotNull List<@Nullable Object> array,
                                     @NotNull ClassTreeWithConverters classTree) {
    var collectionMemberType = classTree.getGenericParameters()[0];
    var stream = array
      .stream()
      .map(jsonElement -> {
        if (jsonElement == null) {
          return null;
        }
        if (collectionMemberType.getJsonDataType() != JsonModelType.determineJsonModelTypeFromStringTreeModel(jsonElement.getClass())) { //todo: move to JsonElement
          throw JsolnException.cannotCovertJsonElementToType(collectionMemberType, jsonElement.getClass());
        }
        return DeserializeUtil.javaifyJsonModel(jsonElement, collectionMemberType);
      });
    return streamToUnmodifiableCollection(stream);
  }

  @SuppressWarnings("unchecked")
  public List<@Nullable Object> toJsonModel(@NotNull Object collection,
                                            @NotNull ClassTreeWithConverters classTree) {
    ClassTreeWithConverters collectionOfWhat = classTree.getGenericParameters()[0];
    return collectionToStream((C) collection)
      .map(it -> SerializeUtil.javaObjectToJsonModel(it, collectionOfWhat))
      .toList();
  }

  protected abstract @UnmodifiableView C streamToUnmodifiableCollection(@NotNull Stream<?> stream);

  protected abstract Stream<?> collectionToStream(@NotNull C flatValue);

}
