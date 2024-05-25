package com.bldrei.jsoln.cache;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Cache {
  private Cache() {}

  private static final Map<Class<?>, ClassDeserializationInfo> classDeserializationCache = new HashMap<>();
  private static final Map<Class<?>, RecordDeserializationInfo> recordDeserializationCache = new HashMap<>();

  public static void clear() {
    classDeserializationCache.clear();
    recordDeserializationCache.clear();
  }

  //todo: validate dto as well

  @NonNull
  public static ClassDeserializationInfo getClassDeserializationInfo(Class<?> clazz) {
    if (clazz.isRecord()) throw new IllegalStateException();
    return classDeserializationCache.computeIfAbsent(clazz, ClassDeserializationInfo::from);
  }

  @NonNull
  public static RecordDeserializationInfo getRecordDeserializationInfo(Class<?> clazz) {
    if (!clazz.isRecord()) throw new IllegalStateException();
    return recordDeserializationCache.computeIfAbsent(clazz, RecordDeserializationInfo::from);
  }

}


