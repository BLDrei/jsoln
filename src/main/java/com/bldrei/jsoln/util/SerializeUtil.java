package com.bldrei.jsoln.util;

import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonObject;

public class SerializeUtil {
  private SerializeUtil() {}

  public static <T> JsonElement convertObjectToJsonElement(T obj, ClassTree classTree) { //todo: pass converter
    Class<?> clazz = classTree.rawType();

    if (AcceptedFieldTypes.isAcceptableTextTypeForField(clazz)) {
      return ConvertersCache.getTextConverter(clazz)
        .orElseThrow(IllegalStateException::new)
        .objectToJsonElement(obj);
    }
    else if (AcceptedFieldTypes.isAcceptableNumberTypeForField(clazz)) {
      return ConvertersCache.getNumberConverter(clazz)
        .orElseThrow(IllegalStateException::new)
        .objectToJsonElement(obj);
    }
    else if (AcceptedFieldTypes.isAcceptableBooleanTypeForField(clazz)) {
      return ConvertersCache.getBooleanConverter()
        .objectToJsonElement(obj);
    }
    else if (AcceptedFieldTypes.isAcceptableArrayTypeForField(clazz)) {
      return ConvertersCache.getArrayConverter(clazz)
        .orElseThrow(IllegalStateException::new)
        .objectToJsonArray(obj, classTree);
    }
    else if (AcceptedFieldTypes.isAcceptableObjectTypeForField(clazz)) {
      return JsonObject.from(obj, classTree);
    }
    throw new JsolnException("Unsupported type: " + classTree);
  }
}
