package com.bldrei.jsoln.util;

import com.bldrei.jsoln.exception.JsonSyntaxException;
import com.bldrei.jsoln.jsonmodel.JsonArray;
import com.bldrei.jsoln.jsonmodel.JsonBoolean;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;
import com.bldrei.jsoln.tokenizer.JsonArrayTokenizer;
import com.bldrei.jsoln.tokenizer.JsonObjectTokenizer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.bldrei.jsoln.Const.CLOSING_BRACKET;
import static com.bldrei.jsoln.Const.CLOSING_CURLY_BRACE;
import static com.bldrei.jsoln.Const.DOUBLE_QUOTE;
import static com.bldrei.jsoln.Const.OPENING_BRACKET;
import static com.bldrei.jsoln.Const.OPENING_CURLY_BRACE;
import static com.bldrei.jsoln.util.StringUtil.isWrapped;
import static com.bldrei.jsoln.util.StringUtil.removeFirstLastChar;

public class DeserializeUtil {

  private DeserializeUtil() {}

  private static final Pattern numericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");

  //returns JsonArray or JsonObject
  public static JsonElement toJsonTree(@NotNull String fullJson) {
    JsonElement jsonTree = parseToAnyJsonElement(fullJson.strip());
    return switch (jsonTree) {
      case JsonObject jo -> jo;
      case JsonArray ja -> ja;
      case null, default -> throw new JsonSyntaxException("Valid json must be wrapped into {} or []");
    };
  }

  //accepts text of any json type (array, object, text, number...)
  //can be used recursively
  private static @Nullable JsonElement parseToAnyJsonElement(@NotNull String json) {
    if (isWrapped(json, OPENING_CURLY_BRACE, CLOSING_CURLY_BRACE)) {
      Map<String, JsonElement> kvMap = new HashMap<>();
      var tokenizer = new JsonObjectTokenizer(removeFirstLastChar(json).strip());

      while (true) {
        var nextKvPair = tokenizer.getNextKvPairAsStrings();
        if (nextKvPair == null) {
          return new JsonObject(kvMap);
        }
        var jsonElem = parseToAnyJsonElement(nextKvPair.getValue());
        if (jsonElem != null) {
          kvMap.put(nextKvPair.getKey(), jsonElem);
        }
      }
    }
    else if (isWrapped(json, OPENING_BRACKET, CLOSING_BRACKET)) {
      List<@Nullable JsonElement> array = new ArrayList<>();
      var tokenizer = new JsonArrayTokenizer(removeFirstLastChar(json));

      while (true) {
        var nextArrayMember = tokenizer.getNextArrayMemberAsString();
        if (nextArrayMember == null) {
          return new JsonArray(array);
        }
        array.add(parseToAnyJsonElement(nextArrayMember));
      }
    }
    else if (isWrapped(json, DOUBLE_QUOTE, DOUBLE_QUOTE)) {
      return new JsonText(removeFirstLastChar(json));
    }
    else if (json.equals("true")) {
      return JsonBoolean.TRUE;
    }
    else if (json.equals("false")) {
      return JsonBoolean.FALSE;
    }
    else if (json.equals("null")) {
      return null;
    }
    else if (numericPattern.matcher(json).matches()) {
      return new JsonNumber(json);
    }

    throw new JsonSyntaxException("Invalid Json parameter value: '%s'".formatted(json));
  }
}
