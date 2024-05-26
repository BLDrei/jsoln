package com.bldrei.jsoln.util;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.Const;
import com.bldrei.jsoln.Jsoln;
import com.bldrei.jsoln.cache.Cache;
import com.bldrei.jsoln.cache.RecordDeserializationInfo;
import com.bldrei.jsoln.cache.RecordFieldInfo;
import com.bldrei.jsoln.exception.JsolnException;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SerializeUtil {
  private SerializeUtil() {}

  public static <T> StringBuffer appendObjectTypeToStringBuffer(T obj, StringBuffer sb) {
    Class<T> clazz = (Class<T>) obj.getClass();

    var recordDeserializationInfo = Cache.getRecordDeserializationInfo(clazz);
    return SerializeUtil.serializeRecordObject(obj, recordDeserializationInfo, sb);
  }

  public static <R> StringBuffer serializeRecordObject(@NonNull R record,
                                                       @NonNull RecordDeserializationInfo<R> recordDeserializationInfo,
                                                       @NonNull StringBuffer sb) {
    sb.append(Const.OPENING_CURLY_BRACE);
    for (RecordFieldInfo recordFieldInfo : recordDeserializationInfo.getFieldsInfo()) {
      Object actualValue = ReflectionUtil.invokeInstanceMethod(record, recordFieldInfo.accessor());
      Object flatValue = switch (actualValue) {
        case null -> null;
        case Optional<?> o -> o.orElse(null);
        default -> actualValue;
      };

      if (flatValue == null && !Configuration.serializeIncludeNull) continue;
      sb
        .append(Const.DOUBLE_QUOTE).append(recordFieldInfo.name()).append(Const.DOUBLE_QUOTE)
        .append(Const.KV_DELIMITER)
        .append(flatValue == null ? null : convertToJsonString(flatValue, recordFieldInfo.classTree()))
        .append(Const.PARAMS_DELIMITER);
    }
    sb.append(Const.CLOSING_CURLY_BRACE);
    return sb;
  }

  @NonNull
  private static String convertToJsonString(@NonNull Object v,
                                            @NonNull ClassTree classTree) {
    if (!classTree.rawType().equals(v.getClass())) {
      throw new JsolnException("Object type mismatch, expected: " + classTree.rawType() + ", actual: " + v.getClass());
    }
    //if is text type, wrap in double quotes and use converter
    //if is number type, do use number converter
    //if boolean, append true/false
    //if object, use
    return switch (v) {
      //str
      case String s -> Const.DOUBLE_QUOTE + s + Const.DOUBLE_QUOTE;
      case LocalDate ld -> Configuration.dateTimeFormatter == null ? ld.toString() : Configuration.dateTimeFormatter.format(ld);
      case LocalDateTime ldt -> Configuration.dateTimeFormatter == null ? ldt.toString() : Configuration.dateTimeFormatter.format(ldt);
      //num
      case Integer i -> Integer.toString(i);
      case Double d -> Double.toString(d);
      case Float f -> Float.toString(f);
      case Long l -> Long.toString(l);
      case Short s -> Short.toString(s);
      case Byte b -> Byte.toString(b);
      //bool
      case Boolean b -> Boolean.toString(b);
      //array
      case List<?> l -> l.stream()
        .map(it -> convertToJsonString(it, classTree.genericParameters()[0]))
        .collect(Collectors.joining(Character.toString(Const.ARRAY_MEMBERS_DELIMITER),
          Character.toString(Const.OPENING_BRACKET), Character.toString(Const.CLOSING_BRACKET)));
      case Set<?> s -> s.stream()
        .map(it -> convertToJsonString(it, classTree.genericParameters()[0]))
        .collect(Collectors.joining(Character.toString(Const.ARRAY_MEMBERS_DELIMITER),
          Character.toString(Const.OPENING_BRACKET), Character.toString(Const.CLOSING_BRACKET)));
      //object
      default -> Jsoln.serialize(v);
    };
  }
}
