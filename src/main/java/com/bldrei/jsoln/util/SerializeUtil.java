package com.bldrei.jsoln.util;

import com.bldrei.jsoln.Const;
import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.jsonmodel.AcceptedFieldTypes;
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

  public static String convertJsonElementToString(JsonElement jsonElement) { //todo: use stringBuffer
    return switch (jsonElement) {
      case JsonBoolean jb -> jb.serialize();
      case JsonNumber jn -> jn.getNumberAsString();
      case JsonText jt -> Const.DOUBLE_QUOTE_STR + jt.getValueAsString() + Const.DOUBLE_QUOTE_STR;
      case JsonObject jo -> jo.getKvMap().entrySet()
        .stream()
        .map(e -> Const.DOUBLE_QUOTE_STR + e.getKey() + Const.DOUBLE_QUOTE_STR
          + Const.KV_DELIMITER_STR + convertJsonElementToString(e.getValue()))
        .collect(Collectors.joining(Const.PARAMS_DELIMITER_STR, Const.OPENING_CURLY_BRACE_STR, Const.CLOSING_CURLY_BRACE_STR));
      case JsonArray ja -> ja.getArray()
        .stream()
        .map(SerializeUtil::convertJsonElementToString)
        .collect(Collectors.joining(Const.ARRAY_MEMBERS_DELIMITER_STR, Const.OPENING_BRACKET_STR, Const.CLOSING_BRACKET_STR));
    };
  }
}
