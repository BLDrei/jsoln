package com.bldrei.jsoln.util;

import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
import com.bldrei.jsoln.jsonmodel.JsonArray;
import com.bldrei.jsoln.jsonmodel.JsonBoolean;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;

public class SerializeUtil {
  private SerializeUtil() {}

  public static <T> JsonElement convertObjectToJsonElement(T obj, ClassTree classTree) {
    Class<?> clazz = classTree.rawType();

    if (AcceptedFieldTypes.isAcceptableTextTypeForField(clazz)) {
      return JsonText.from(obj, clazz);
    }
    else if (AcceptedFieldTypes.isAcceptableNumberTypeForField(clazz)) {
      return JsonNumber.from(obj, clazz);
    }
    else if (AcceptedFieldTypes.isAcceptableBooleanTypeForField(clazz)) {
      return JsonBoolean.from(obj, clazz);
    }
    else if (AcceptedFieldTypes.isAcceptableArrayTypeForField(clazz)) {
      return JsonArray.from(obj, classTree);
    }
    else if (AcceptedFieldTypes.isAcceptableObjectTypeForField(clazz)) {
      return JsonObject.from(obj, classTree);
    }
    throw new JsolnException("Unsupported type: " + classTree);
  }
}
