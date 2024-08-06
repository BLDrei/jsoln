package com.bldrei.jsoln.cache;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Cache {
  private Cache() {}

  private static final Map<Class<?>, RecordDeserializationInfo<?>> recordDeserializationCache = new HashMap<>();

  public static void clear() {
    recordDeserializationCache.clear();
  }

  //todo: validate dto as well

  @SuppressWarnings("unchecked")
  public static <R> @NotNull RecordDeserializationInfo<R> getRecordDeserializationInfo(Class<R> clazz) {
    if (!clazz.isRecord()) throw new IllegalStateException();
    return (RecordDeserializationInfo<R>) recordDeserializationCache.computeIfAbsent(clazz, RecordDeserializationInfo::from);
  }

}


