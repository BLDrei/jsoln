package com.bldrei.jsoln.tokenizer;

import java.util.Optional;
import java.util.Stack;

import static com.bldrei.jsoln.Const.ARRAY_MEMBERS_DELIMITER;
import static com.bldrei.jsoln.Const.CLOSING_BRACKET;
import static com.bldrei.jsoln.Const.CLOSING_CURLY_BRACE;
import static com.bldrei.jsoln.Const.OPENING_BRACKET;
import static com.bldrei.jsoln.Const.OPENING_CURLY_BRACE;

public class JsonArrayTokenizer extends AbstractJsonTokenizer {

  public JsonArrayTokenizer(String txt) {
    super(txt);
  }

  public Optional<String> getNextArrayMemberAsString() {
    char[] remainingChars = remainingTxt.toCharArray();
    String arrayMember;

    Stack<Character> openingBrackets = new Stack<>();
    for (int i = 0; i < remainingChars.length; i++) {
      char currentChar = remainingChars[i];
      if (currentChar == ARRAY_MEMBERS_DELIMITER && openingBrackets.empty()) {
        arrayMember = remainingTxt.substring(0, i);
        remainingTxt = remainingTxt.substring(i + 1); //starting from next char after comma
        return Optional.of(arrayMember.trim());
      }
      if (i == remainingChars.length - 1 && openingBrackets.empty()) {
        arrayMember = remainingTxt;
        remainingTxt = "";
        return Optional.of(arrayMember.trim());
      }
      else if (currentChar == OPENING_CURLY_BRACE || currentChar == OPENING_BRACKET) {
        openingBrackets.push(currentChar);
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

    return Optional.empty();
  }
}
