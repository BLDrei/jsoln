package com.bldrei.jsoln.cache;

import java.util.HashMap;
import java.util.Map;

public class Cache {
  private Cache() {}

  public static final Map<Class<?>, ClassDeserializationInfo> classDeserializationCache = new HashMap<>(); //todo: private
  public static final Map<Class<?>, RecordDeserializationInfo> recordDeserializationCache = new HashMap<>(); //todo: private

  public static boolean isDtoTypeCached(Class<?> clazz) {
    if (clazz.isRecord()) {
      return recordDeserializationCache.containsKey(clazz);
    }
    return classDeserializationCache.containsKey(clazz);
  }

  public static void cacheIfUnknown(Class<?> clazz) { //todo: validate dto as well
    if (isDtoTypeCached(clazz)) return;

    if (clazz.isRecord()) {
      var recordDeserializationInfo = RecordDeserializationInfo.from(clazz);
      recordDeserializationCache.put(clazz, recordDeserializationInfo);
    }
    else {
      var classDeserializationInfo = ClassDeserializationInfo.from(clazz);
      classDeserializationCache.put(clazz, classDeserializationInfo);
    }
  }

}


