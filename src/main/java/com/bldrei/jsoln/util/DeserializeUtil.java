package com.bldrei.jsoln.util;

import com.bldrei.jsoln.converter.array.ArrayConverter;
import com.bldrei.jsoln.converter.bool.BooleanConverter;
import com.bldrei.jsoln.converter.number.NumberConverter;
import com.bldrei.jsoln.converter.object.ObjectConverter;
import com.bldrei.jsoln.converter.text.TextConverter;
import com.bldrei.jsoln.exception.JsolnException;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class DeserializeUtil {

  private DeserializeUtil() {}

//  private static final Pattern numericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");

  public static Object javaifyJsonModel(@NotNull Object jsonElement, @NotNull ClassTreeWithConverters classTree) {
    return switch (classTree.getConverter()) {
      case TextConverter<?> tc
        when jsonElement instanceof String s -> tc.javaify(s);
      case NumberConverter<?> nc
        when jsonElement instanceof Number n -> nc.javaify(n);
      case BooleanConverter bc
        when jsonElement instanceof Boolean b -> b;
      case ArrayConverter<?> ac
        when jsonElement instanceof List l -> ac.javaify((List<Object>) l, classTree);
      case ObjectConverter<?> oc
        when jsonElement instanceof Map m -> oc.javaify((Map<String, Object>) m, classTree);
      default -> throw JsolnException.cannotCovertJsonElementToType(classTree, jsonElement.getClass());
    };
  }

//  //returns JsonArray or JsonObject
//  public static JsonElement toJsonTree(@NotNull String fullJson) {
//    JsonElement jsonTree = parseToAnyJsonElement(fullJson.strip());
//    return switch (jsonTree) {
//      case JsonObject jo -> jo;
//      case JsonArray ja -> ja;
//      case null, default -> throw new JsonSyntaxException("Valid json must be wrapped into {} or []");
//    };
//  }
//
//  //accepts text of any json type (array, object, text, number...)
//  //can be used recursively
//  private static @Nullable JsonElement parseToAnyJsonElement(@NotNull String json) {
//    if (isWrapped(json, OPENING_CURLY_BRACE, CLOSING_CURLY_BRACE)) {
//      Map<String, JsonElement> kvMap = new HashMap<>();
//      var tokenizer = new JsonObjectTokenizer(removeFirstLastChar(json).strip());
//
//      while (true) {
//        var nextKvPair = tokenizer.getNextKvPairAsStrings();
//        if (nextKvPair == null) {
//          return new JsonObject(kvMap);
//        }
//        var jsonElem = parseToAnyJsonElement(nextKvPair.getValue());
//        if (jsonElem != null) {
//          kvMap.put(nextKvPair.getKey(), jsonElem);
//        }
//      }
//    }
//    else if (isWrapped(json, OPENING_BRACKET, CLOSING_BRACKET)) {
//      List<@Nullable JsonElement> array = new ArrayList<>();
//      var tokenizer = new JsonArrayTokenizer(removeFirstLastChar(json));
//
//      while (true) {
//        var nextArrayMember = tokenizer.getNextArrayMemberAsString();
//        if (nextArrayMember == null) {
//          return new JsonArray(array);
//        }
//        array.add(parseToAnyJsonElement(nextArrayMember));
//      }
//    }
//    else if (isWrapped(json, DOUBLE_QUOTE, DOUBLE_QUOTE)) {
//      return new JsonText(removeFirstLastChar(json));
//    }
//    else if (json.equals("true")) {
//      return JsonBoolean.TRUE;
//    }
//    else if (json.equals("false")) {
//      return JsonBoolean.FALSE;
//    }
//    else if (json.equals("null")) {
//      return null;
//    }
//    else if (numericPattern.matcher(json).matches()) {
//      return new JsonNumber(json);
//    }
//
//    throw new JsonSyntaxException("Invalid Json parameter value: '%s'".formatted(json));
//  }
}
