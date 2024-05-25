package com.bldrei.jsoln.cache;

import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.util.ReflectionUtil;
import lombok.NonNull;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Cache {
  private Cache() {}

  private static final Map<Class<?>, ClassDeserializationInfo<?>> classDeserializationCache = new HashMap<>();
  private static final Map<Class<?>, RecordDeserializationInfo<?>> recordDeserializationCache = new HashMap<>();
  private static final Map<Class<?>, Method> enumValueOfCache = new HashMap<>();

  public static void clear() {
    classDeserializationCache.clear();
    recordDeserializationCache.clear();
    enumValueOfCache.clear();
  }

  //todo: validate dto as well

  @NonNull
  @SuppressWarnings({"unchecked", "unused"})
  public static <C> ClassDeserializationInfo<C> getClassDeserializationInfo(Class<C> clazz) {
    if (clazz.isRecord()) throw new IllegalStateException();
    return (ClassDeserializationInfo<C>) classDeserializationCache.computeIfAbsent(clazz, ClassDeserializationInfo::from);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static <R> RecordDeserializationInfo<R> getRecordDeserializationInfo(Class<R> clazz) {
    if (!clazz.isRecord()) throw new IllegalStateException();
    return (RecordDeserializationInfo<R>) recordDeserializationCache.computeIfAbsent(clazz, RecordDeserializationInfo::from);
  }

  @NonNull
  public static Method getEnumValueOf(Class<?> clazz) {
    if (!clazz.isEnum()) throw new IllegalStateException();

    if (enumValueOfCache.get(clazz) == null) {
      Method valueOf = ReflectionUtil.findPublicMethod(clazz, "valueOf", String.class)
        .orElseThrow(() -> new BadDtoException("public valueOf(String) method not found for enum: " + clazz));
      enumValueOfCache.put(clazz, valueOf);
    }
    return enumValueOfCache.get(clazz);
  }

}


