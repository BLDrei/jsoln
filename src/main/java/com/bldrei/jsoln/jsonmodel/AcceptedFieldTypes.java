package com.bldrei.jsoln.jsonmodel;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@UtilityClass
public class AcceptedFieldTypes {

  public static Set<Class<?>> OBJECT_FINAL_TYPES = Set.of(Map.class);
  public static Set<Class<?>> ARRAY_FINAL_TYPES = Set.of(List.class, Set.class);
  public static Set<Class<?>> TEXT_FINAL_TYPES = Set.of(String.class, LocalDate.class, LocalDateTime.class);
  public static Set<Class<?>> NUMBER_FINAL_TYPES = Set.of(
    Short.class, Integer.class, Long.class, Double.class,
    Float.class, BigDecimal.class, BigInteger.class, Byte.class
  );
  public static Set<Class<?>> BOOLEAN_FINAL_TYPES = Set.of(Boolean.class, boolean.class);

  public static Class<?> OBJECT_TYPE_RECORD = Record.class;
  public static Class<?> TEXT_TYPES_ENUM = Enum.class;

  public boolean isAcceptableObjectTypeForField(@NonNull Class<?> type) {
    return OBJECT_TYPE_RECORD.isAssignableFrom(type) || OBJECT_FINAL_TYPES.contains(type);
  }

  public boolean isAcceptableArrayTypeForField(@NonNull Class<?> type) {
    return ARRAY_FINAL_TYPES.contains(type);
  }

  public boolean isAcceptableTextTypeForField(@NonNull Class<?> type) {
    return TEXT_FINAL_TYPES.contains(type)
      || TEXT_TYPES_ENUM.isAssignableFrom(type);
  }

  public boolean isAcceptableNumberTypeForField(@NonNull Class<?> type) {
    return NUMBER_FINAL_TYPES.contains(type);
  }

  public boolean isAcceptableBooleanTypeForField(@NonNull Class<?> type) {
    return BOOLEAN_FINAL_TYPES.contains(type);
  }

  public boolean isActualObjectTypeMatchingWithFieldType(Class<?> actualType, Class<?> fieldType) {
    return fieldType == actualType
      || isAcceptableObjectTypeForField(fieldType)
      || isAcceptableArrayTypeForField(fieldType);
  }
}
