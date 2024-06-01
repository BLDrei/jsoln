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
public class AcceptedTypes {

  public boolean isAcceptableObjectTypeForField(@NonNull Class<?> type) {
    return type.isRecord() || Map.class == type;
  }

  public boolean isAcceptableArrayTypeForField(@NonNull Class<?> type) {
    return List.class == type || Set.class == type;
  }

  public boolean isAcceptableTextTypeForField(@NonNull Class<?> type) {
    return String.class == type
      || type.isEnum()
      || LocalDate.class == type
      || LocalDateTime.class == type;
  }

  public boolean isAcceptableNumberTypeForField(@NonNull Class<?> type) {
    return Short.class == type
      || Integer.class == type
      || Long.class == type
      || Double.class == type
      || Float.class == type
      || BigDecimal.class == type
      || BigInteger.class == type
      || Byte.class == type;
  }

  public boolean isAcceptableBooleanTypeForField(@NonNull Class<?> type) {
    return Boolean.class == type || boolean.class == type;
  }

  public boolean isActualObjectTypeMatchingWithFieldType(Class<?> actualType, Class<?> fieldType) {
    return fieldType == actualType
      || isAcceptableObjectTypeForField(fieldType)
      || isAcceptableArrayTypeForField(fieldType);
  }
}
