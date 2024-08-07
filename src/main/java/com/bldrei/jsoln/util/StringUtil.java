package com.bldrei.jsoln.util;

import org.jetbrains.annotations.NotNull;

public class StringUtil {
  private StringUtil() {}

  public static @NotNull String removeFirstLastChar(@NotNull String txt) {
    return txt.substring(1, txt.length() - 1);
  }

  public static boolean isWrapped(@NotNull String json, char firstChar, char lastChar) {
    return json.length() >= 2
      && json.charAt(0) == firstChar
      && json.charAt(json.length() - 1) == lastChar;
  }
}
