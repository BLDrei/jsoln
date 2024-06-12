package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.exception.JsolnException;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AcceptedFieldTypes {
  private AcceptedFieldTypes() {}

  private static final Set<Class<?>> OBJECT_FINAL_TYPES = Set.of(Map.class);
  private static final Set<Class<?>> ARRAY_FINAL_TYPES = Set.of(List.class, Set.class);
  private static final Set<Class<?>> TEXT_FINAL_TYPES = Set.of(String.class, LocalDate.class, LocalDateTime.class);
  private static final Set<Class<?>> NUMBER_FINAL_TYPES = Set.of(
    Short.class, Integer.class, Long.class, Double.class,
    Float.class, BigDecimal.class, BigInteger.class, Byte.class
  );
  private static final Set<Class<?>> BOOLEAN_FINAL_TYPES = Set.of(Boolean.class, boolean.class);

  private static final Class<?> OBJECT_TYPE_RECORD = Record.class;
  private static final Class<?> TEXT_TYPE_ENUM = Enum.class;

  private static boolean isAcceptableObjectTypeForField(@NonNull Class<?> type) {
    return OBJECT_TYPE_RECORD.isAssignableFrom(type) || OBJECT_FINAL_TYPES.contains(type);
  }

  private static boolean isAcceptableArrayTypeForField(@NonNull Class<?> type) {
    return ARRAY_FINAL_TYPES.contains(type);
  }

  private static boolean isAcceptableTextTypeForField(@NonNull Class<?> type) {
    return TEXT_FINAL_TYPES.contains(type)
      || TEXT_TYPE_ENUM.isAssignableFrom(type);
  }

  private static boolean isAcceptableNumberTypeForField(@NonNull Class<?> type) {
    return NUMBER_FINAL_TYPES.contains(type);
  }

  private static boolean isAcceptableBooleanTypeForField(@NonNull Class<?> type) {
    return BOOLEAN_FINAL_TYPES.contains(type);
  }

  public static JsonElement.Type determineJsonDataType(@NonNull Class<?> type) {
    return switch (type) {
      case Class<?> cl when isAcceptableObjectTypeForField(cl) -> JsonElement.Type.OBJECT;
      case Class<?> cl when isAcceptableArrayTypeForField(cl) -> JsonElement.Type.ARRAY;
      case Class<?> cl when isAcceptableTextTypeForField(cl) -> JsonElement.Type.TEXT;
      case Class<?> cl when isAcceptableNumberTypeForField(cl) -> JsonElement.Type.NUMBER;
      case Class<?> cl when isAcceptableBooleanTypeForField(cl) -> JsonElement.Type.BOOLEAN;
      default -> throw new JsolnException("Unsupported field type: " + type);
    };
  }

  public static boolean isActualObjectTypeMatchingWithFieldType(@NonNull Class<?> actualType, @NonNull Class<?> fieldType) {
    return fieldType == actualType
      || isAcceptableObjectTypeForField(fieldType)
      || isAcceptableArrayTypeForField(fieldType);
  }
}
