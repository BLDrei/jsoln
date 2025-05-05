package com.bldrei.jsoln.cache;

import com.bldrei.jsoln.configuration.Configuration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Cache {
  public Cache() {}

  private final Map<Class<?>, RecordDeserializationInfo<?>> recordDeserializationCache = new HashMap<>();

  @SuppressWarnings("unchecked")
  public <R> @NotNull RecordDeserializationInfo<R> getRecordDeserializationInfo(Class<R> clazz, Configuration conf) {
    if (!clazz.isRecord()) throw new IllegalStateException();
    return (RecordDeserializationInfo<R>) recordDeserializationCache.computeIfAbsent(clazz, it -> RecordDeserializationInfo.from(it, conf));
  }

}


