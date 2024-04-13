package com.bldrei.jsoln.tokenizer;

import com.bldrei.jsoln.util.StringUtil;

import java.util.Map;
import java.util.Optional;
import java.util.Stack;

import static com.bldrei.jsoln.Const.CLOSING_BRACKET;
import static com.bldrei.jsoln.Const.CLOSING_CURLY_BRACE;
import static com.bldrei.jsoln.Const.DOUBLE_QUOTE;
import static com.bldrei.jsoln.Const.KV_DELIMITER;
import static com.bldrei.jsoln.Const.OPENING_BRACKET;
import static com.bldrei.jsoln.Const.OPENING_CURLY_BRACE;
import static com.bldrei.jsoln.Const.PARAMS_DELIMITER;

public final class JsonObjectTokenizer extends AbstractJsonTokenizer {

  public JsonObjectTokenizer(String txt) {
    super(txt);
  }

  public Optional<Map.Entry<String, String>> getNextKvPairAsStrings() {
    char[] remainingChars = remainingTxt.toCharArray();
    int i = 0;
    int kvDelimeterIndex = -1;
    String key = null;
    String value = null;

    for (; i < remainingChars.length; i++) {
      if (remainingChars[i] != KV_DELIMITER) {
        continue;
      }
      String possibleKey = remainingTxt.substring(0, i).trim(); //trim for parsing pretty formatted jsons
      if (!StringUtil.isWrapped(possibleKey, DOUBLE_QUOTE, DOUBLE_QUOTE)) {
        throw new IllegalArgumentException("Illegal json syntax: key is not wrapped into double quotes: " + possibleKey);
      }
      key = StringUtil.removeFirstLastChar(possibleKey);
      kvDelimeterIndex = i;
      i++;
      break;
    }

    if (key == null) return Optional.empty(); //to do: remove

    Stack<Character> openingBrackets = new Stack<>();
    for (; i < remainingChars.length; i++) {
      char currentChar = remainingChars[i];
      if (currentChar == PARAMS_DELIMITER && openingBrackets.empty()) {
        value = remainingTxt.substring(kvDelimeterIndex + 1, i).trim(); //trim for parsing pretty formatted jsons
        remainingTxt = remainingTxt.substring(i + 1); //starting from next char after comma
        return Optional.of(Map.entry(key, value));
      }
      else if (currentChar == OPENING_CURLY_BRACE || currentChar == OPENING_BRACKET) {
        openingBrackets.push(currentChar); //not implemented
      }
      else if (currentChar == CLOSING_CURLY_BRACE) {
        if (openingBrackets.peek() == OPENING_CURLY_BRACE) openingBrackets.pop();
        else throw new IllegalArgumentException("Brackets opening-closing order");
      }
      else if (currentChar == CLOSING_BRACKET) {
        if (openingBrackets.peek() == OPENING_BRACKET) openingBrackets.pop();
        else throw new IllegalArgumentException("Brackets order fucked up");
      }
    }

    value = remainingTxt.substring(kvDelimeterIndex + 1).trim(); //trim for parsing pretty formatted jsons
    remainingTxt = "";
    return Optional.of(Map.entry(key, value));

  }
}
