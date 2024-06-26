package com.bldrei.jsoln.util;

import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import lombok.Getter;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

@Getter
public final class ClassTreeWithConverters { //todo: rename, it's probably not a Tree

  private static final ClassTreeWithConverters[] EMPTY = new ClassTreeWithConverters[]{};
  private final Class<?> rawType;
  private final JsonElement.Type jsonDataType;
  private final AbstractConverter converter;
  private final ClassTreeWithConverters[] genericParameters;


  private ClassTreeWithConverters(@NonNull Class<?> rawType,
                                  @NonNull JsonElement.Type jsonDataType,
                                  @NonNull AbstractConverter converter,
                                  @NonNull ClassTreeWithConverters[] genericParameters) {
    this.rawType = rawType;
    this.jsonDataType = jsonDataType;
    this.converter = converter;
    this.genericParameters = genericParameters;
  }

  public static ClassTreeWithConverters fromField(Field fld) {
    return fromType(fld.getGenericType());
  }

  public static ClassTreeWithConverters fromClass(Class<?> clazz) {
    return fromType(clazz);
  }

  public static ClassTreeWithConverters fromType(Type type) {
    if (type instanceof ParameterizedType parameterizedType) {
      Class<?> clazz = (Class<?>) parameterizedType.getRawType();
      JsonElement.Type jsonDataType = AcceptedFieldTypes.determineJsonDataType(clazz); //todo: throw exceptions specifying dto and field name
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
      JsonElement.Type jsonDataType = AcceptedFieldTypes.determineJsonDataType(clazz);
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
