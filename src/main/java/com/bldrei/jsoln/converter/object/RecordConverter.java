package com.bldrei.jsoln.converter.object;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.RequiredFieldsDefinitionMode;
import com.bldrei.jsoln.cache.RecordDeserializationInfo;
import com.bldrei.jsoln.cache.RecordFieldInfo;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.exception.MissingValueException;
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
                   @NotNull ClassTreeWithConverters classTree,
                   @NotNull Configuration conf) {
    var recordDeserializationInfo = (RecordDeserializationInfo<R>) conf.getCache().getRecordDeserializationInfo(classTree.getRawType(), conf);
    var kvMap = jsonNode.properties()
      .stream()
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    boolean areOptionalsAllowed = conf.getRequiredFieldsDefinitionMode() == RequiredFieldsDefinitionMode.STRICT;

    Object[] params = recordDeserializationInfo.getFieldsInfo().stream().map(recordComponent -> {
      boolean isRequired = recordComponent.isRequired();
      var value = Optional.ofNullable(kvMap.get(recordComponent.name()));
      boolean valuePresent = value.filter(it -> !it.isNull()).isPresent();

      if (valuePresent) {
        if (JsonModelType.determineJsonModelTypeFromJsonNode(value.get()) != recordComponent.jsonModelType()) {
          throw JsolnException.cannotCovertJsonElementToType(recordComponent.classTree(), value.get().getClass());
        }
        Object valueOfActualType = DeserializeUtil.javaifyJsonModel(value.get(), recordComponent.classTree(), conf);
        return !isRequired && areOptionalsAllowed ? Optional.ofNullable(valueOfActualType) : valueOfActualType;
      }

      if (isRequired) {
        if (areOptionalsAllowed) {
          throw new MissingValueException("Value not present, but field '%s' is mandatory on dto class %s".formatted(recordComponent.name(), recordComponent.dtoClass()));
        }
        return null;
      }

      return areOptionalsAllowed ? Optional.empty() : null;

    }).toArray();

    return ReflectionUtil.invokeCanonicalConstructor(recordDeserializationInfo.getCanonicalConstructor(), params);
  }

  @Override
  protected Map<String, String> toJsonModelMutableMap(@NotNull R obj,
                                                      @NotNull ClassTreeWithConverters classTree,
                                                      @NotNull Configuration conf) {
    var recordDeserializationInfo = conf.getCache().getRecordDeserializationInfo(classTree.getRawType(), conf);
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
        SerializeUtil.stringify(flatValue, rc.classTree(), null, conf)
      );
    }
    return kvMap;
  }
}
