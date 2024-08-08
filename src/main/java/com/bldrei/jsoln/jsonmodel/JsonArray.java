package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.Const;
import com.bldrei.jsoln.converter.array.ArrayConverter;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AllArgsConstructor
public final class JsonArray implements JsonElement {

  private final List<@Nullable JsonElement> array;

  public Object toObject(ClassTreeWithConverters classTree) {
    return ((ArrayConverter<?>) classTree.getConverter())
      .jsonElementsToObject(array, classTree);
  }

  public StringBuffer appendToSB(StringBuffer sb) {
    sb.append(Const.OPENING_BRACKET_STR);

    var iterator = array.iterator();
    iterator.forEachRemaining(el -> {
      if (el == null) {
        sb.append("null");
      }
      else {
        el.appendToSB(sb);
      }
      if (iterator.hasNext()) {
        sb.append(Const.ARRAY_MEMBERS_DELIMITER_STR);
      }
    });

    sb.append(Const.CLOSING_BRACKET_STR);
    return sb;
  }
}
