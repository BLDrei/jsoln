package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTree;
import com.bldrei.jsoln.util.SerializeUtil;
import lombok.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MapConverter extends ObjectConverter<Map> {
  public MapConverter() {
    super(Map.class);
  }

  @Override
  public Map convert(Stream<?> stream) {
    return Map.of(); //todo: implement and use
  }

  @Override
  protected Map<String, JsonElement> toJsonElementMap(@NonNull Map map, ClassTree classTree) {
    if (map.isEmpty()) {
      return Collections.emptyMap();
    }

    var valueType = classTree.genericParameters()[1];
    return ((Map<?, ?>) map).entrySet().stream()
      .collect(Collectors.toUnmodifiableMap(
        e -> (String) e.getKey(), //hm, what?
        e -> SerializeUtil.convertObjectToJsonElement(e.getValue(), valueType)
      ));
  }
}
