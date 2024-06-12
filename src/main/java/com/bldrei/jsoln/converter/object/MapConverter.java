package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.converter.text.TextConverter;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.SerializeUtil;
import lombok.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public final class MapConverter extends ObjectConverter<Map<?, ?>> {

  @Override
  public Map<?, ?> jsonElementsMapToObject(@NonNull Map<String, JsonElement> kvMap,
                                           @NonNull ClassTreeWithConverters classTree) {
    ClassTreeWithConverters keyType = classTree.getGenericParameters()[0];
    ClassTreeWithConverters valueType = classTree.getGenericParameters()[1];
    if (keyType.getJsonDataType() != JsonElement.Type.TEXT) { //todo: move to dto validator
      throw new BadDtoException("According to json syntax, key for JsonObject may only be JsonText");
    }
    return kvMap.entrySet().stream()
      .collect(Collectors.toUnmodifiableMap(
        e -> ((TextConverter<?>) keyType.getConverter()).stringToObject(e.getKey()),
        e -> e.getValue().toObject(valueType),
        (k1, k2) -> new IllegalStateException("Duplicate keys %s and %s".formatted(k1, k2))
      ));
  }

  @Override
  protected Map<String, JsonElement> objectToJsonElementsMap(@NonNull Map<?, ?> map,
                                                             @NonNull ClassTreeWithConverters classTree) {
    if (map.isEmpty()) {
      return Collections.emptyMap();
    }

    var valueType = classTree.getGenericParameters()[1];
    return map.entrySet().stream()
      .collect(Collectors.toUnmodifiableMap(
        e -> (String) e.getKey(), //hm, what?
        e -> SerializeUtil.convertObjectToJsonElement(e.getValue(), valueType)
      ));
  }
}
