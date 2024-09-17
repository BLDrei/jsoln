package com.bldrei.jsoln.util;

import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonModelType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClassTreeWithConverters { //todo: rename, it's probably not a Tree

  private static final ClassTreeWithConverters[] EMPTY = new ClassTreeWithConverters[]{};

  private final @NotNull Class<?> rawType;
  private final @NotNull JsonModelType jsonDataType;
  private final @NotNull AbstractConverter converter;
  private final @NotNull ClassTreeWithConverters[] genericParameters;

  public static ClassTreeWithConverters fromClass(Class<?> clazz) {
    return fromType(clazz);
  }

  public static ClassTreeWithConverters fromType(Type type) {
    if (type instanceof ParameterizedType parameterizedType) {
      Class<?> clazz = (Class<?>) parameterizedType.getRawType();
      JsonModelType jsonDataType = AcceptedFieldTypes.determineFieldJsonDataType(clazz); //todo: throw exceptions specifying dto and field name
      return new ClassTreeWithConverters(
        clazz,
        jsonDataType,
        ConvertersCache.getConverter(clazz, jsonDataType),
        Arrays.stream(parameterizedType.getActualTypeArguments())
          .map(ClassTreeWithConverters::fromType)
          .toArray(ClassTreeWithConverters[]::new)
      );
    }

    if (type instanceof Class<?> clazz) {
      JsonModelType jsonDataType = AcceptedFieldTypes.determineFieldJsonDataType(clazz);
      return new ClassTreeWithConverters(
        clazz,
        jsonDataType,
        ConvertersCache.getConverter(clazz, jsonDataType),
        EMPTY
      );
    }

    if (type instanceof GenericArrayType) {
      throw new BadDtoException("Arrays are not allowed as field types");
    }
    if (type instanceof TypeVariable<?>) {
      throw new BadDtoException("Unexpected field type '%s'".formatted(type.getTypeName()));
    }
    if (type instanceof WildcardType) {
      throw new IllegalStateException("Not possible to have WildcardType as class/record field type");
    }

    throw new IllegalStateException();
  }
}
