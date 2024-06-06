package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.cache.Cache;
import com.bldrei.jsoln.cache.RecordDeserializationInfo;
import com.bldrei.jsoln.cache.RecordFieldInfo;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTree;
import com.bldrei.jsoln.util.ReflectionUtil;
import com.bldrei.jsoln.util.SerializeUtil;
import lombok.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class RecordConverter<R> extends ObjectConverter<R> {
  public RecordConverter(Class<R> type) {
    super(type);
  }

  @Override
  @SuppressWarnings("unchecked")
  public R convert(Map<String, JsonElement> kvMap, ClassTree classTree) {
    var recordDeserializationInfo = (RecordDeserializationInfo<R>) Cache.getRecordDeserializationInfo(classTree.rawType());
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
  protected Map<String, JsonElement> toJsonElementMap(@NonNull R obj, ClassTree classTree) {
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
      kvMap.put(
        recordFieldInfo.name(),
        SerializeUtil.convertObjectToJsonElement(flatValue, recordFieldInfo.classTree())
      );
    }
    return kvMap;
  }
}
