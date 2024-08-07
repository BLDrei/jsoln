package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.exception.BadDtoException;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
  private static final Class<?> BOOLEAN_FINAL_TYPE = Boolean.class;

  private static final Class<?> OBJECT_TYPE_RECORD = Record.class;
  private static final Class<?> TEXT_TYPE_ENUM = Enum.class;

  private static boolean isAcceptableObjectTypeForField(@NotNull Class<?> type) {
    return OBJECT_TYPE_RECORD == type.getSuperclass()
      || OBJECT_FINAL_TYPES.contains(type);
  }

  private static boolean isAcceptableArrayTypeForField(@NotNull Class<?> type) {
    return ARRAY_FINAL_TYPES.contains(type);
  }

  private static boolean isAcceptableTextTypeForField(@NotNull Class<?> type) {
    return TEXT_FINAL_TYPES.contains(type)
      || TEXT_TYPE_ENUM == type.getSuperclass();
  }

  private static boolean isAcceptableNumberTypeForField(@NotNull Class<?> type) {
    return NUMBER_FINAL_TYPES.contains(type);
  }

  private static boolean isAcceptableBooleanTypeForField(@NotNull Class<?> type) {
    return BOOLEAN_FINAL_TYPE == type;
  }

  public static @NotNull JsonElement.Type determineJsonDataType(@NotNull Class<?> clazz) {
    if (isAcceptableObjectTypeForField(clazz)) return JsonElement.Type.OBJECT;
    if (isAcceptableArrayTypeForField(clazz)) return JsonElement.Type.ARRAY;
    if (isAcceptableTextTypeForField(clazz)) return JsonElement.Type.TEXT;
    if (isAcceptableNumberTypeForField(clazz)) return JsonElement.Type.NUMBER;
    if (isAcceptableBooleanTypeForField(clazz)) return JsonElement.Type.BOOLEAN;

    if (Optional.class == clazz) {
      throw new BadDtoException("Optional is only allowed as dto field type wrapping layer");
    }
    throw new BadDtoException("Unsupported field type: " + clazz.getName());
  }
}
