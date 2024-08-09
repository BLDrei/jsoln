package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.Const;
import com.bldrei.jsoln.converter.text.TextConverter;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public final class JsonText implements JsonElement {
  private final @NotNull String valueAsString;

  public Object toObject(ClassTreeWithConverters classTree) {
    return ((TextConverter<?>) classTree.getConverter())
      .stringToObject(valueAsString);
  }

  public StringBuffer appendToSB(StringBuffer sb) {
    return sb
      .append(Const.DOUBLE_QUOTE_STR)
      .append(valueAsString)
      .append(Const.DOUBLE_QUOTE_STR);
  }
}
