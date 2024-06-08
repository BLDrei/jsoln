package com.bldrei.jsoln.util;

import com.bldrei.jsoln.converter.array.ArrayConverter;
import com.bldrei.jsoln.converter.bool.BooleanConverter;
import com.bldrei.jsoln.converter.number.NumberConverter;
import com.bldrei.jsoln.converter.object.ObjectConverter;
import com.bldrei.jsoln.converter.text.TextConverter;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonElement;

public class SerializeUtil {
  private SerializeUtil() {}

  public static <T> JsonElement convertObjectToJsonElement(T obj, ClassTreeWithConverters classTree) { //todo: pass converter
    Class<?> clazz = classTree.getRawType();

    if (AcceptedFieldTypes.isAcceptableTextTypeForField(clazz)) {
      return ((TextConverter<?>) classTree.getConverter())
        .objectToJsonElement(obj);
    }
    else if (AcceptedFieldTypes.isAcceptableNumberTypeForField(clazz)) {
      return ((NumberConverter<?>) classTree.getConverter())
        .objectToJsonElement(obj);
    }
    else if (AcceptedFieldTypes.isAcceptableBooleanTypeForField(clazz)) {
      return ((BooleanConverter) classTree.getConverter())
        .objectToJsonElement(obj);
    }
    else if (AcceptedFieldTypes.isAcceptableArrayTypeForField(clazz)) {
      return ((ArrayConverter<?>) classTree.getConverter())
        .objectToJsonArray(obj, classTree);
    }
    else if (AcceptedFieldTypes.isAcceptableObjectTypeForField(clazz)) {
      return ((ObjectConverter<?>) classTree.getConverter())
        .objectToJsonObject(obj, classTree);
    }
    throw new JsolnException("Unsupported type: " + classTree);
  }
}
