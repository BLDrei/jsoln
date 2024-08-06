package com.bldrei.jsoln.util;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;

public class ReflectionUtil {
  private ReflectionUtil() {}

  public static Method findEnumValueOfMethod(@NotNull Class<?> clazz) {
    if (!clazz.isEnum()) throw new IllegalStateException();

    try {
      return clazz.getDeclaredMethod("valueOf", String.class);
    }
    catch (NoSuchMethodException e) {
      throw new IllegalStateException(e);
    }
  }

  @SneakyThrows
  public static Object invokeStaticMethod(@NotNull Method method, Object... args) {
    return method.invoke(null, args);
  }

  @SneakyThrows
  public static Object invokeInstanceMethod(@NotNull Object obj, @NotNull Method method, Object... args) {
    return method.invoke(obj, args);
  }

  @SneakyThrows
  public static <T> T invokeConstructor(@NotNull Constructor<T> constructor, Object... args) {
    return constructor.newInstance(args);
  }

  public static <R> Constructor<R> findCanonicalConstructor(@NotNull Class<R> recordClass) {
    if (!recordClass.isRecord()) throw new IllegalStateException();

    Class<?>[] types = Arrays.stream(recordClass.getRecordComponents())
      .map(RecordComponent::getType)
      .toArray(Class[]::new);
    try {
      return recordClass.getDeclaredConstructor(types);
    }
    catch (NoSuchMethodException wtf) {
      throw new IllegalStateException("No canonical constructor for a record? Java, what's wrong with you?", wtf);
    }
  }

}
