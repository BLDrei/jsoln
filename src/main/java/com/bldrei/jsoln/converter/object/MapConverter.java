package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTree;
import com.bldrei.jsoln.util.SerializeUtil;
import lombok.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public final class MapConverter extends ObjectConverter<Map> {
  public MapConverter() {
    super(Map.class);
  }

  @Override
  public Map convert(Map<String, JsonElement> kvMap, ClassTree classTree) {
    ClassTree keyType = classTree.genericParameters()[0];
    ClassTree valueType = classTree.genericParameters()[1];
    if (!String.class.equals(keyType.rawType())) { //todo: move to dto validator
      throw new BadDtoException("According to json syntax, key for JsonObject may only be JsonText");
    }
    return kvMap.entrySet().stream()
      .collect(Collectors.toUnmodifiableMap(
        Map.Entry::getKey,
        e -> e.getValue().toObject(valueType),
        (k1, k2) -> new IllegalStateException("Duplicate keys %s and %s".formatted(k1, k2))
      ));
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
