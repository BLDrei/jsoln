package com.bldrei.jsoln.util;

import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public record ClassTreeWithConverters(Class<?> rawType, AbstractConverter converter, ClassTreeWithConverters[] genericParameters) { //todo: rename, it's probably not a Tree

  public static final ClassTreeWithConverters[] EMPTY = new ClassTreeWithConverters[]{};

  public ClassTreeWithConverters {
    Objects.requireNonNull(rawType);
    Objects.requireNonNull(genericParameters);
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
      return new ClassTreeWithConverters(
        clazz,
        ConvertersCache.getConverter(clazz, AcceptedFieldTypes.determineJsonDataType(clazz)),
        Arrays.stream(parameterizedType.getActualTypeArguments())
          .map(ClassTreeWithConverters::fromType)
          .toArray(ClassTreeWithConverters[]::new)
      );
    }

    Class<?> clazz = (Class<?>) type;
    return new ClassTreeWithConverters(
      clazz,
      ConvertersCache.getConverter(clazz, AcceptedFieldTypes.determineJsonDataType(clazz)),
      EMPTY
    );
  }
}
