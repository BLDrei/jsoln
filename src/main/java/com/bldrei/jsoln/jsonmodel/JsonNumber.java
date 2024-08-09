package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.converter.number.NumberConverter;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public final class JsonNumber implements JsonElement {
  private final @NotNull String numberAsString;

  public Object toObject(ClassTreeWithConverters classTree) {
    return ((NumberConverter<?>) classTree.getConverter())
      .stringToObject(numberAsString);
  }

  public StringBuffer appendToSB(StringBuffer sb) {
    return sb.append(numberAsString);
  }
}
