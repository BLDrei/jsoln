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
    return type.isRecord() || Map.class.equals(type);
  }

  public boolean isAcceptableArrayTypeForField(@NonNull Class<?> type) {
    return List.class.equals(type) || Set.class.equals(type);
  }

  public boolean isAcceptableTextTypeForField(@NonNull Class<?> type) {
    return String.class.equals(type)
      || type.isEnum()
      || LocalDate.class.equals(type)
      || LocalDateTime.class.equals(type);
  }

  public boolean isAcceptableNumberTypeForField(@NonNull Class<?> type) {
    return Short.class.equals(type)
      || Integer.class.equals(type)
      || Long.class.equals(type)
      || Double.class.equals(type)
      || Float.class.equals(type)
      || BigDecimal.class.equals(type)
      || BigInteger.class.equals(type)
      || Byte.class.equals(type);
  }

  public boolean isAcceptableBooleanTypeForField(@NonNull Class<?> type) {
    return Boolean.class.equals(type) || boolean.class.equals(type);
  }

  public boolean isActualObjectTypeMatchingWithFieldType(Class<?> actualType, Class<?> fieldType) {
    return fieldType.equals(actualType)
      || ((Map.class.equals(fieldType) || List.class.equals(fieldType) || Set.class.equals(fieldType))
          && fieldType.isAssignableFrom(actualType));
  }
}
