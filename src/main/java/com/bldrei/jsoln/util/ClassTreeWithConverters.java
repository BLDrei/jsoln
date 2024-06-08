package com.bldrei.jsoln.util;

import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import lombok.Getter;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

@Getter
public final class ClassTreeWithConverters { //todo: rename, it's probably not a Tree

  private static final ClassTreeWithConverters[] EMPTY = new ClassTreeWithConverters[]{};
  private final Class<?> rawType;
  private final AbstractConverter converter;
  private final ClassTreeWithConverters[] genericParameters;


  private ClassTreeWithConverters(@NonNull Class<?> rawType,
                                  @NonNull AbstractConverter converter,
                                  @NonNull ClassTreeWithConverters[] genericParameters) {
    this.rawType = rawType;
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
