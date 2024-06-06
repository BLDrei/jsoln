package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.Const;
import com.bldrei.jsoln.cache.Cache;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.util.ClassTree;
import com.bldrei.jsoln.util.ReflectionUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public final class JsonObject implements JsonElement {
  Map<String, JsonElement> kvMap;

  private Optional<JsonElement> get(String fieldName) {
    return Optional.ofNullable(kvMap.get(fieldName));
  }

  public Object toObject(@NonNull ClassTree classTree) {
    if (Map.class.equals(classTree.rawType())) {
      Class<?> keyType = classTree.genericParameters()[0].rawType();
      if (!String.class.equals(keyType)) {
        throw new RuntimeException("Not implemented yet");
      }
      return kvMap.entrySet().stream()
        .collect(Collectors.toUnmodifiableMap(
          Map.Entry::getKey,
          e -> e.getValue().toObject(classTree.genericParameters()[1]),
          (k1, k2) -> new IllegalStateException("Duplicate keys %s and %s".formatted(k1, k2))
        ));
    }

    var recordDeserializationInfo = Cache.getRecordDeserializationInfo(classTree.rawType());
    Object[] params = recordDeserializationInfo.getFieldsInfo().stream().map(recordComponent -> {
      boolean isNullable = recordComponent.isNullable();
      var value = this.get(recordComponent.name());
      boolean valuePresent = value.isPresent();

      if (valuePresent) {
        if (value.get().getJsonDataType() != recordComponent.jsonType()) {
          throw new JsolnException("For field '" + recordComponent.name() + "', expected json type is " + recordComponent.jsonType() + ", but received " + value.get().getJsonDataType());
        }
        Object valueOfActualType = value.get().toObject(recordComponent.classTree());
        return isNullable ? Optional.ofNullable(valueOfActualType) : valueOfActualType;
      }
      else if (isNullable) {
        return Optional.empty();
      }
      else {
        Configuration.missingRequiredValueHandler.accept(recordComponent.name(), recordComponent.dtoClass());
        return null;
      }
    }).toArray();

    return ReflectionUtil.invokeConstructor(recordDeserializationInfo.getCanonicalConstructor(), params);
  }

  public String serialize() {
    return kvMap.entrySet()
      .stream()
      .map(e -> Const.DOUBLE_QUOTE_STR + e.getKey() + Const.DOUBLE_QUOTE_STR
        + Const.KV_DELIMITER_STR + e.getValue().serialize())
      .collect(Collectors.joining(Const.PARAMS_DELIMITER_STR, Const.OPENING_CURLY_BRACE_STR, Const.CLOSING_CURLY_BRACE_STR));
  }
}
