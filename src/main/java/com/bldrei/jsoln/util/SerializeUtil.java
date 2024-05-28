package com.bldrei.jsoln.util;

import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.AcceptedTypes;
import com.bldrei.jsoln.jsonmodel.JsonArray;
import com.bldrei.jsoln.jsonmodel.JsonBoolean;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;

import java.util.stream.Collectors;

public class SerializeUtil {
  private SerializeUtil() {}

  public static <T> JsonElement convertObjectToJsonElement(T obj, ClassTree classTree) {
    Class<?> clazz = classTree.rawType();

    if (AcceptedTypes.isAcceptableTextTypeForField(clazz)) {
      return JsonText.from(obj, clazz);
    }
    else if (AcceptedTypes.isAcceptableNumberTypeForField(clazz)) {
      return JsonNumber.from(obj, (Class<? extends Number>) clazz);
    }
    else if (AcceptedTypes.isAcceptableBooleanTypeForField(clazz)) {
      return JsonBoolean.from(obj, clazz);
    }
    else if (AcceptedTypes.isAcceptableArrayTypeForField(clazz)) {
      return JsonArray.from(obj, classTree);
    }
    else if (AcceptedTypes.isAcceptableObjectTypeForField(clazz)) {
      return JsonObject.from(obj, classTree);
    }
    throw new JsolnException("Unsupported type: " + classTree);
  }

  public static String convertJsonElementToString(JsonElement jsonElement) { //todo: use stringBuffer
    return switch (jsonElement) {
      case JsonBoolean jb -> Boolean.toString(jb.value());
      case JsonNumber jn -> jn.getNumberAsString();
      case JsonText jt -> "\"" + jt.getValueAsString() + "\"";
      case JsonObject jo -> jo.getKvMap().entrySet()
        .stream()
        .map(e -> "\"" + e.getKey() + "\":" + convertJsonElementToString(e.getValue()))
        .collect(Collectors.joining(",", "{", "}"));
      case JsonArray ja -> ja.getArray()
        .stream()
        .map(SerializeUtil::convertJsonElementToString)
        .collect(Collectors.joining(",", "[", "]"));
    };
  }
}
