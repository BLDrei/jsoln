package com.bldrei.jsoln.converter.array;

import com.bldrei.jsoln.configuration.Configuration;
import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.JsonModelType;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.DeserializeUtil;
import com.bldrei.jsoln.util.SerializeUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract sealed class ArrayConverter<C>
  implements AbstractConverter
  permits ListConverter {

  public @UnmodifiableView C javaify(@NotNull JsonNode jsonNode,
                                     @NotNull ClassTreeWithConverters classTree,
                                     @NotNull Configuration conf) {
    var collectionMemberType = classTree.getGenericParameters()[0];
    var newArrayList = new ArrayList<>();
    jsonNode.iterator()
      .forEachRemaining(jsonElement -> {
        if (jsonElement.isNull()) {
          newArrayList.add(null);
        }
        else if (collectionMemberType.getJsonDataType() != JsonModelType.determineJsonModelTypeFromJsonNode(jsonElement)) {
          throw JsolnException.cannotCovertJsonElementToType(collectionMemberType, jsonElement.getClass());
        }
        else {
          newArrayList.add(DeserializeUtil.javaifyJsonModel(jsonElement, collectionMemberType, conf));
        }
      });
    return arrayToUnmodifiableCollection(newArrayList);
  }

  @SuppressWarnings("unchecked")
  public String stringify(@NotNull Object collection,
                          @NotNull ClassTreeWithConverters classTree,
                          StringBuilder sb,
                          @NotNull Configuration conf) {
    ClassTreeWithConverters collectionOfWhat = classTree.getGenericParameters()[0];
    return collectionToStream((C) collection)
      .map(it -> SerializeUtil.stringify(it, collectionOfWhat, sb, conf))
      .collect(Collectors.joining(",", "[", "]"));
  }

  protected abstract @UnmodifiableView C arrayToUnmodifiableCollection(@NotNull List<?> stream);

  protected abstract Stream<?> collectionToStream(@NotNull C flatValue);

}
