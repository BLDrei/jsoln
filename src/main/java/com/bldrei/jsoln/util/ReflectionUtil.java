package com.bldrei.jsoln.util;

import lombok.NonNull;
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

  public static Optional<Method> findPublicMethod(Class<?> clazz, String name, Class<?>... params) {
    try {
      return Optional.of(clazz.getDeclaredMethod(name, params))
        .filter(m -> Modifier.isPublic(m.getModifiers()));
    }
    catch (NoSuchMethodException e) {
      return Optional.empty();
    }
  }

  public static Optional<Method> findGetter(Class<?> dto, String fldName, Class<?> param) {
    String prefix = boolean.class.equals(param) ? "is" : "get";
    return findPublicMethod(dto, prefix + capitalizeFirstLetter(fldName), param)
//      .filter(m -> m.getReturnType().equals(param))
      ;
  }

  public static Optional<Method> findSetter(Class<?> dto, String fldName, Class<?> param) {
    return findPublicMethod(dto, "set" + capitalizeFirstLetter(fldName), param);
  }

  @SneakyThrows
  public static Object invokeStaticMethod(@NonNull Method method, Object... args) {
    return method.invoke(null, args);
  }

  @SneakyThrows
  public static Object invokeInstanceMethod(@NonNull Object obj, @NonNull Method method, Object... args) {
    return method.invoke(obj, args);
  }

  @SneakyThrows
  public static <T> T invokeConstructor(@NonNull Constructor<T> constructor, Object... args) {
    return constructor.newInstance(args);
  }

  private static String capitalizeFirstLetter(String str) {
    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
  }

  public static Field[] getPrivateNonStaticFields(@NonNull Class<?> clazz) {
    if (clazz.isRecord()) throw new IllegalStateException("Use clazz.getRecordComponents() for this");

    return Arrays.stream(clazz.getDeclaredFields())
      .filter(field -> Modifier.isPrivate(field.getModifiers()))
      .filter(field -> !Modifier.isStatic(field.getModifiers()))
      .toArray(Field[]::new);
  }

  public static <R> Constructor<R> findCanonicalConstructor(@NonNull Class<R> recordClass) {
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

  public static <C> Optional<Constructor<C>> findNoArgsConstructor(@NonNull Class<C> tClass) {
    if (tClass.isRecord()) throw new IllegalStateException();

    try {
      return Optional.of(tClass.getDeclaredConstructor());
    }
    catch (NoSuchMethodException e) {
      return Optional.empty();
    }
  }
}
