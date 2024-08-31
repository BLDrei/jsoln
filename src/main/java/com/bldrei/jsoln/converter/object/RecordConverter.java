package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.cache.Cache;
import com.bldrei.jsoln.cache.RecordDeserializationInfo;
import com.bldrei.jsoln.cache.RecordFieldInfo;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.ReflectionUtil;
import com.bldrei.jsoln.util.SerializeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class RecordConverter<R> extends ObjectConverter<R> {

  @Override
  @SuppressWarnings("unchecked")
  public R jsonElementsMapToObject(@NotNull Map<String, JsonElement> kvMap,
                                   @NotNull ClassTreeWithConverters classTree) {
    var recordDeserializationInfo = (RecordDeserializationInfo<R>) Cache.getRecordDeserializationInfo(classTree.getRawType());
    Object[] params = recordDeserializationInfo.getFieldsInfo().stream().map(recordComponent -> {
      boolean isNullable = recordComponent.isNullable();
      var value = Optional.ofNullable(kvMap.get(recordComponent.name()));
      boolean valuePresent = value.isPresent();

      if (valuePresent) {
        if (value.get().getJsonDataType() != recordComponent.jsonType()) {
          throw JsolnException.cannotCovertJsonElementToType(recordComponent.classTree(), value.get());
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

    return ReflectionUtil.invokeCanonicalConstructor(recordDeserializationInfo.getCanonicalConstructor(), params);
  }

  @Override
  protected Map<String, JsonElement> objectToJsonElementsMutableMap(@NotNull R obj,
                                                                    @NotNull ClassTreeWithConverters classTree) {
    var recordDeserializationInfo = Cache.getRecordDeserializationInfo(classTree.getRawType());
    Map<String, JsonElement> kvMap = new LinkedHashMap<>(recordDeserializationInfo.getFieldsInfo().size());

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
        SerializeUtil.convertObjectToJsonElement(flatValue, rc.classTree())
      );
    }
    return kvMap;
  }
}
