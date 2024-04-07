package com.bldrei.jsoln.util;

import com.bldrei.jsoln.exception.JsolnException;
import com.bldrei.jsoln.exception.JsonSyntaxException;
import com.bldrei.jsoln.jsonmodel.JsonArray;
import com.bldrei.jsoln.jsonmodel.JsonBoolean;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonNumber;
import com.bldrei.jsoln.jsonmodel.JsonObject;
import com.bldrei.jsoln.jsonmodel.JsonText;
import com.bldrei.jsoln.tokenizer.JsonArrayTokenizer;
import com.bldrei.jsoln.tokenizer.JsonObjectTokenizer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.bldrei.jsoln.Const.CLOSING_BRACKET;
import static com.bldrei.jsoln.Const.CLOSING_CURLY_BRACE;
import static com.bldrei.jsoln.Const.DOUBLE_QUOTE;
import static com.bldrei.jsoln.Const.OPENING_BRACKET;
import static com.bldrei.jsoln.Const.OPENING_CURLY_BRACE;

public class DeserializeUtil {

  private DeserializeUtil() {}

  private static final Pattern numericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");

  //returns JsonArray or JsonObject
  public static JsonObject parseFullJson(String fullJson) {
    if (isWrapped(fullJson, OPENING_CURLY_BRACE, CLOSING_CURLY_BRACE)) {
      return (JsonObject) parseToJsonElement(fullJson);
    }
    //to do: if (isWrapped(json, [, ]))

    throw new JsonSyntaxException("Valid json must be wrapped into {} or []");
  }

  //accepts text of any json type (array, object, text, number...)
  //can be used recursively
  private static JsonElement parseToJsonElement(String json)
  {
    if (isWrapped(json, OPENING_CURLY_BRACE, CLOSING_CURLY_BRACE)) {
      Map<String, JsonElement> kvMap = new HashMap<>();
      var tokenizer = new JsonObjectTokenizer(removeFirstLastChar(json));

      while (true) {
        var nextKvPair = tokenizer.getNextKvPairAsStrings();
        if (nextKvPair.isEmpty()) {
          return new JsonObject(kvMap);
        }
        kvMap.put(nextKvPair.get().getKey(), parseToJsonElement(nextKvPair.get().getValue()));
      }
    }
    else if (isWrapped(json, OPENING_BRACKET, CLOSING_BRACKET)) {
      List<JsonElement> array = new LinkedList<>(); //come up with more optimal solution
      var tokenizer = new JsonArrayTokenizer(removeFirstLastChar(json));

      while (true) {
        var nextArrayMember = tokenizer.getNextArrayMemberAsString();
        if (nextArrayMember.isEmpty()) {
          return new JsonArray(array);
        }
        array.add(parseToJsonElement(nextArrayMember.get()));
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

  public static <T> T getNewEmptyInstance(Class<T> tClass) {
    try {
      return tClass.getDeclaredConstructor().newInstance();
    }
    catch (NoSuchMethodException e) {
      throw new JsolnException("Zero-argument constructor missing for " + tClass);
    }
    catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> Constructor<T> getCanonicalConstructor(Class<T> record, RecordComponent[] recordComponents) {
    Class<?>[] types = Arrays.stream(recordComponents)
      .map(c -> c.getType()).toArray(Class[]::new);
    try {
      return record.getDeclaredConstructor(types);
    }
    catch (NoSuchMethodException e) {
      throw new JsolnException("Canonical constructor missing for " + record);
    }
  }

  public static String removeFirstLastChar(String txt) {
    return txt.substring(1, txt.length() - 1);
  }

  public static boolean isWrapped(String json, char firstChar, char lastChar) {
    return json.length() >= 2
      && json.charAt(0) == firstChar
      && json.charAt(json.length() - 1) == lastChar;
  }
}
