package com.bldrei.jsoln;import com.bldrei.jsoln.cache.Cache;import com.bldrei.jsoln.cache.RecordDeserializationInfo;import com.bldrei.jsoln.jsonmodel.JsonObject;import com.bldrei.jsoln.util.DeserializeUtil;import com.bldrei.jsoln.util.SerializeUtil;public final class Jsoln {  private Jsoln() {}  public static <T> String serialize(T obj) {    if (!obj.getClass().isRecord()) throw new IllegalStateException("Map or classes not implemented yet");    var recordDeserInfo = Cache.getRecordDeserializationInfo(obj.getClass()).getClassTree(); //todo: deser info for ser????    StringBuffer sb = switch (SerializeUtil.convertObjectToJsonElement(obj, recordDeserInfo)) {      case JsonObject jo -> jo.appendToSB(new StringBuffer());      default -> throw new IllegalStateException();    };    return sb.toString();  }  public static <T> T deserialize(String fullJson, Class<T> tClass) {    return deserialize((JsonObject) DeserializeUtil.toJsonTree(fullJson), tClass);  }  public static <T> T deserialize(JsonObject jsonObject, Class<T> tClass) {    if (!tClass.isRecord()) throw new IllegalStateException();    return deserializeRecordObject(jsonObject, tClass);  }  private static <R> R deserializeRecordObject(JsonObject jsonObject, Class<R> tClass) {    RecordDeserializationInfo<R> recordDeserializationInfo = Cache.getRecordDeserializationInfo(tClass);    return (R) jsonObject.toObject(recordDeserializationInfo.getClassTree());  }}