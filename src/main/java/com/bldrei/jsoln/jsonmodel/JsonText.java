package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.Const;
import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.util.ClassTree;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class JsonText implements JsonElement {
  private final String valueAsString;

  public Object toObject(ClassTree classTree) {
    return ConvertersCache.getTextConverter(classTree.rawType())
      .stringToObject(valueAsString);
  }

  public StringBuffer appendToSB(StringBuffer sb) {
    return sb
      .append(Const.DOUBLE_QUOTE_STR)
      .append(valueAsString)
      .append(Const.DOUBLE_QUOTE_STR);
  }
}
