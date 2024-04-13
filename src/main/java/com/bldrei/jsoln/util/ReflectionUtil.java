package com.bldrei.jsoln.util;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.Optional;

public class ReflectionUtil {
  private ReflectionUtil() {}

  public static Optional<Method> findMethod(Class<?> clazz, String name, Class<?>... params) {
    try {
      return Optional.of(clazz.getMethod(name, params));
    }
    catch (NoSuchMethodException e) {
      return Optional.empty();
    }
  }

  public static Optional<Method> findDeclaredMethod(Class<?> clazz, String name, Class<?>... params) {
    try {
      return Optional.of(clazz.getDeclaredMethod(name, params));
    }
    catch (NoSuchMethodException e) {
      return Optional.empty();
    }
  }

  public static Optional<Method> findGetter(Class<?> dto, String fldName, Class<?> param) {
    String prefix = boolean.class.equals(param) ? "is" : "get";
    return findDeclaredMethod(dto, prefix + capitalizeFirstLetter(fldName), param)
      .filter(m -> m.getReturnType().equals(param));
  }

  public static Optional<Method> findSetter(Class<?> dto, String fldName, Class<?> param) {
    return findDeclaredMethod(dto, "set" + capitalizeFirstLetter(fldName), param);
  }

  @SneakyThrows
  public static Object invokeMethod(Object obj, Method method, Object... args) {
    return method.invoke(obj, args);
  }

  @SneakyThrows
  public static <T> T invokeConstructor(Constructor<T> constructor, Object... args) {
    return constructor.newInstance(args);
  }

  public static String capitalizeFirstLetter(String str) {
    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
  }

  public static Field[] getNonStaticFields(Class<?> clazz) {
    if (clazz.isRecord()) throw new IllegalStateException("Use record components for this");

    return Arrays.stream(clazz.getDeclaredFields())
      .filter(field -> !Modifier.isStatic(field.getModifiers()))
      .toArray(Field[]::new);
  }

  @SneakyThrows(NoSuchMethodException.class)
  public static <T> Constructor<T> getCanonicalConstructor(Class<T> recordClass) {
    if (!recordClass.isRecord()) throw new IllegalStateException();

    Class<?>[] types = Arrays.stream(recordClass.getRecordComponents())
      .map(RecordComponent::getType)
      .toArray(Class[]::new);
    return recordClass.getDeclaredConstructor(types);
  }

  public static <T> Optional<Constructor<?>> getNoArgsConstructor(Class<T> tClass) { //todo: put T instead of ?
    if (tClass.isRecord()) throw new IllegalStateException();

    try {
      return Optional.of(tClass.getDeclaredConstructor());
    }
    catch (NoSuchMethodException e) {
      return Optional.empty();
    }
  }

  public static <T> Optional<Constructor<?>> getAllArgsConstructor(Class<T> tClass, Field[] nonStaticFields) { //todo: put T instead of ?
    if (tClass.isRecord()) throw new IllegalStateException();

    try {
      return Optional.of(tClass.getDeclaredConstructor(Arrays.stream(nonStaticFields).map(Field::getType).toArray(Class[]::new)));
    }
    catch (NoSuchMethodException e) {
      return Optional.empty();
    }
  }
}
