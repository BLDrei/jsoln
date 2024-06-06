package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.Const;
import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.util.ClassTree;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public final class JsonObject implements JsonElement {
  Map<String, JsonElement> kvMap;

  public Object toObject(@NonNull ClassTree classTree) {
    return ConvertersCache.getObjectConverter(classTree.rawType())
      .convert(kvMap, classTree);
  }

  public String serialize() {
    return kvMap.entrySet()
      .stream()
      .map(e -> Const.DOUBLE_QUOTE_STR + e.getKey() + Const.DOUBLE_QUOTE_STR
        + Const.KV_DELIMITER_STR + e.getValue().serialize())
      .collect(Collectors.joining(Const.PARAMS_DELIMITER_STR, Const.OPENING_CURLY_BRACE_STR, Const.CLOSING_CURLY_BRACE_STR));
  }
}
