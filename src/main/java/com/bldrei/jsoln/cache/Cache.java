package com.bldrei.jsoln.cache;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Cache {
  private Cache() {}

  private static final Map<Class<?>, ClassDeserializationInfo<?>> classDeserializationCache = new HashMap<>();
  private static final Map<Class<?>, RecordDeserializationInfo<?>> recordDeserializationCache = new HashMap<>();

  public static void clear() {
    classDeserializationCache.clear();
    recordDeserializationCache.clear();
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

}


