package com.bldrei.jsoln.util;

public class StringUtil {
  private StringUtil() {}

  public static String removeFirstLastChar(String txt) {
    return txt.substring(1, txt.length() - 1);
  }

  public static boolean isWrapped(String json, char firstChar, char lastChar) {
    return json.length() >= 2
      && json.charAt(0) == firstChar
      && json.charAt(json.length() - 1) == lastChar;
  }
}
