package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.cache.Cache;
import com.bldrei.jsoln.cache.RecordDeserializationInfo;
import com.bldrei.jsoln.cache.RecordFieldInfo;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.ReflectionUtil;
import com.bldrei.jsoln.util.SerializeUtil;
import lombok.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class RecordConverter<R> extends ObjectConverter<R> {

  @Override
  @SuppressWarnings("unchecked")
  public R jsonElementsMapToObject(@NonNull Map<String, JsonElement> kvMap,
                                   @NonNull ClassTreeWithConverters classTree) {
    var recordDeserializationInfo = (RecordDeserializationInfo<R>) Cache.getRecordDeserializationInfo(classTree.getRawType());
    Object[] params = recordDeserializationInfo.getFieldsInfo().stream().map(recordComponent -> {
      boolean isNullable = recordComponent.isNullable();
      var value = Optional.ofNullable(kvMap.get(recordComponent.name()));
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

  @Override
  protected Map<String, JsonElement> objectToJsonElementsMap(@NonNull R obj,
                                                             @NonNull ClassTreeWithConverters classTree) {
    var recordDeserializationInfo = Cache.getRecordDeserializationInfo(classTree.getRawType());
    Map<String, JsonElement> kvMap = new LinkedHashMap<>(recordDeserializationInfo.getFieldsInfo().size());

    for (RecordFieldInfo recordFieldInfo : recordDeserializationInfo.getFieldsInfo()) {
      Object actualValue = ReflectionUtil.invokeInstanceMethod(obj, recordFieldInfo.accessor());
      Object flatValue = switch (actualValue) {
        case null -> null;
        case Optional<?> o -> o.orElse(null);
        default -> actualValue;
      };

      if (flatValue == null) continue;

      if (!AcceptedFieldTypes.isActualObjectTypeMatchingWithFieldType(flatValue.getClass(), recordFieldInfo.classTree().getRawType())) {
        throw new JsolnException("Object type mismatch, expected: " + recordFieldInfo.classTree().getRawType() + ", actual: " + flatValue.getClass());
      }
      kvMap.put(
        recordFieldInfo.name(),
        SerializeUtil.convertObjectToJsonElement(flatValue, recordFieldInfo.classTree())
      );
    }
    return kvMap;
  }
}
