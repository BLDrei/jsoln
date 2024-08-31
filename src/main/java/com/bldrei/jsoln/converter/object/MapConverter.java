package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.converter.text.TextConverter;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.SerializeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public final class MapConverter extends ObjectConverter<Map<?, ?>> {

  @Override
  public Map<?, ?> jsonElementsMapToObject(@NotNull Map<String, JsonElement> kvMap,
                                           @NotNull ClassTreeWithConverters classTree) {
    ClassTreeWithConverters keyType = classTree.getGenericParameters()[0];
    ClassTreeWithConverters valueType = classTree.getGenericParameters()[1];
    if (keyType.getJsonDataType() != JsonElement.Type.TEXT) { //todo: move to dto validator
      throw new BadDtoException("According to json syntax, key for JsonObject may only be JsonText");
    }
    var map = kvMap.entrySet().stream()
      .collect(Collectors.toMap(
        e -> ((TextConverter<?>) keyType.getConverter()).stringToObject(e.getKey()),
        e -> e.getValue().toObject(valueType),
        (k1, k2) -> new IllegalStateException("Duplicate keys %s and %s".formatted(k1, k2))
      ));
    return Collections.unmodifiableMap(map);
  }

  @Override
  protected Map<String, JsonElement> objectToJsonElementsMutableMap(@NotNull Map<?, ?> map,
                                                                    @NotNull ClassTreeWithConverters classTree) {
    var valueType = classTree.getGenericParameters()[1];
    return map.entrySet().stream()
      .collect(Collectors.toMap(
        e -> (String) e.getKey(), //hm, what?
        e -> SerializeUtil.convertObjectToJsonElement(e.getValue(), valueType)
      ));
  }
}
