package com.bldrei.jsoln.util;

import com.bldrei.jsoln.exception.JsolnReflectionException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;

public class ReflectionUtil {
  private ReflectionUtil() {}

  public static @NotNull Method findEnumValueOfMethod(@NotNull Class<?> clazz) {
    if (!clazz.isEnum()) throw new IllegalStateException();

    try {
      return clazz.getDeclaredMethod("valueOf", String.class);
    }
    catch (NoSuchMethodException e) {
      throw new IllegalStateException(e);
    }
  }

  public static Object invokeStaticMethod(@NotNull Method method, Object... args) {
    return invokeMethod(null, method, args);
  }

  public static Object invokeInstanceMethod(@NotNull Object obj, @NotNull Method method, Object... args) {
    return invokeMethod(obj, method, args);
  }

  private static Object invokeMethod(Object obj, @NotNull Method method, Object... args) {
    try {
      return method.invoke(obj, args);
    }
    catch (ReflectiveOperationException e) {
      throw new JsolnReflectionException(e);
    }
  }

  public static <R> R invokeCanonicalConstructor(@NotNull Constructor<R> constructor, Object... args) {
    try {
      return constructor.newInstance(args);
    }
    catch (ReflectiveOperationException e) {
      throw new JsolnReflectionException(e);
    }
  }

  public static <R> @NotNull Constructor<R> findCanonicalConstructor(@NotNull Class<R> recordClass) {
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
