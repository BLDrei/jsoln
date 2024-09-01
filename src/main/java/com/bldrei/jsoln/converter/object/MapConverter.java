package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.converter.text.TextConverter;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.SerializeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
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

    Map<Object, Object> map = new HashMap<>();
    for (var e : kvMap.entrySet()) {
      var je = e.getValue();
      map.put(
        ((TextConverter<?>) keyType.getConverter()).stringToObject(e.getKey()),
        je == null ? null : je.toObject(valueType)
      );
    }
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
