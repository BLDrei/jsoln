package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.converter.text.TextConverter;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.JsonModelType;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.DeserializeUtil;
import com.bldrei.jsoln.util.SerializeUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class MapConverter extends ObjectConverter<Map<?, ?>> {

  @Override
  public Map<?, ?> javaify(@NotNull JsonNode jsonNode,
                           @NotNull ClassTreeWithConverters classTree,
                           @NotNull Configuration conf) {
    ClassTreeWithConverters keyType = classTree.getGenericParameters()[0];
    ClassTreeWithConverters valueType = classTree.getGenericParameters()[1];
    if (keyType.getJsonDataType() != JsonModelType.TEXT) {
      throw new BadDtoException("According to json syntax, key for JsonObject may only be JsonText");
    }

    var kvMap = jsonNode.properties()
      .stream()
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    Map<Object, Object> map = new HashMap<>();
    for (var e : kvMap.entrySet()) {
      var je = e.getValue();
      map.put(
        ((TextConverter<?>) keyType.getConverter()).javaify(e.getKey(), conf),
        je == null ? null : DeserializeUtil.javaifyJsonModel(je, valueType, conf)
      );
    }
    return Collections.unmodifiableMap(map);
  }

  @Override
  protected Map<String, String> toJsonModelMutableMap(@NotNull Map<?, ?> map,
                                                      @NotNull ClassTreeWithConverters classTree,
                                                      @NotNull Configuration conf) {
    var valueType = classTree.getGenericParameters()[1];
    return map.entrySet().stream()
      .collect(Collectors.toMap(
        e -> (String) e.getKey(),
        e -> SerializeUtil.stringify(e.getValue(), valueType, null, conf)
      ));
  }
}
