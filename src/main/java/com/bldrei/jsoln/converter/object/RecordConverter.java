package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.cache.Cache;
import com.bldrei.jsoln.cache.RecordDeserializationInfo;
import com.bldrei.jsoln.cache.RecordFieldInfo;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.JsonModelType;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.DeserializeUtil;
import com.bldrei.jsoln.util.ReflectionUtil;
import com.bldrei.jsoln.util.SerializeUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class RecordConverter<R> extends ObjectConverter<R> {

  @Override
  @SuppressWarnings("unchecked")
  public R javaify(@NotNull JsonNode jsonNode,
                   @NotNull ClassTreeWithConverters classTree) {
    var recordDeserializationInfo = (RecordDeserializationInfo<R>) Cache.getRecordDeserializationInfo(classTree.getRawType());
    var kvMap = jsonNode.properties()
      .stream()
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    Object[] params = recordDeserializationInfo.getFieldsInfo().stream().map(recordComponent -> {
      boolean isNullable = recordComponent.isNullable();
      var value = Optional.ofNullable(kvMap.get(recordComponent.name()));
      boolean valuePresent = value.isPresent() && !value.get().isNull();

      if (valuePresent) {
        if (JsonModelType.determineJsonModelTypeFromJsonNode(value.get()) != recordComponent.jsonModelType()) { //todo: move to JsonElement conversion logic
          throw JsolnException.cannotCovertJsonElementToType(recordComponent.classTree(), value.get().getClass());
        }
        Object valueOfActualType = DeserializeUtil.javaifyJsonModel(value.get(), recordComponent.classTree());
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

    return ReflectionUtil.invokeCanonicalConstructor(recordDeserializationInfo.getCanonicalConstructor(), params);
  }

  @Override
  protected Map<String, String> toJsonModelMutableMap(@NotNull R obj,
                                                      @NotNull ClassTreeWithConverters classTree) {
    var recordDeserializationInfo = Cache.getRecordDeserializationInfo(classTree.getRawType());
    Map<String, String> kvMap = new LinkedHashMap<>(recordDeserializationInfo.getFieldsInfo().size());

    for (RecordFieldInfo rc : recordDeserializationInfo.getFieldsInfo()) {
      Object actualValue = ReflectionUtil.invokeInstanceMethod(obj, rc.accessor());
      Object flatValue = switch (actualValue) {
        case null -> null;
        case Optional<?> o -> o.orElse(null);
        default -> actualValue;
      };

      if (flatValue == null) continue;

      kvMap.put(
        rc.name(),
        SerializeUtil.stringify(flatValue, rc.classTree(), null)
      );
    }
    return kvMap;
  }
}
