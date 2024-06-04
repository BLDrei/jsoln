package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.cache.Cache;
import com.bldrei.jsoln.cache.RecordFieldInfo;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.util.ClassTree;
import com.bldrei.jsoln.util.ReflectionUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bldrei.jsoln.util.SerializeUtil.convertJsonElementToString;
import static com.bldrei.jsoln.util.SerializeUtil.convertObjectToJsonElement;

@Getter
@AllArgsConstructor
public final class JsonObject implements JsonElement {
  Map<String, JsonElement> kvMap;

  private Optional<JsonElement> get(String fieldName) {
    return Optional.ofNullable(kvMap.get(fieldName));
  }

  public Object toObject(@NonNull ClassTree classTree) {
    if (Map.class.equals(classTree.rawType())) {
      Type keyType = classTree.genericParameters()[0].rawType();
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

  public static JsonObject from(Object obj, ClassTree classTree) {
    if (Map.class.equals(classTree.rawType())) {
      Map<String, JsonElement> kvMap = ((Map<?, ?>) obj).entrySet().stream()
        .collect(Collectors.toUnmodifiableMap(
          e -> convertJsonElementToString(convertObjectToJsonElement(e.getKey(), classTree.genericParameters()[0])),
          e -> convertObjectToJsonElement(e.getValue(), classTree.genericParameters()[1])
        ));
      return new JsonObject(kvMap);
    }
    var recordDeserializationInfo = Cache.getRecordDeserializationInfo(classTree.rawType());
    Map<String, JsonElement> kvMap = new LinkedHashMap<>(recordDeserializationInfo.getFieldsInfo().size());
    for (RecordFieldInfo recordFieldInfo : recordDeserializationInfo.getFieldsInfo()) {
      Object actualValue = ReflectionUtil.invokeInstanceMethod(obj, recordFieldInfo.accessor());
      Object flatValue = switch (actualValue) {
        case null -> null;
        case Optional<?> o -> o.orElse(null);
        default -> actualValue;
      };

      if (flatValue == null) continue;

      if (!AcceptedFieldTypes.isActualObjectTypeMatchingWithFieldType(flatValue.getClass(), recordFieldInfo.classTree().rawType())) {
        throw new JsolnException("Object type mismatch, expected: " + recordFieldInfo.classTree().rawType() + ", actual: " + flatValue.getClass());
      }
      kvMap.put(recordFieldInfo.name(), convertObjectToJsonElement(flatValue, recordFieldInfo.classTree()));
    }
    return new JsonObject(kvMap);
  }
}
