package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.Const;
import com.bldrei.jsoln.cache.ConvertersCache;
import com.bldrei.jsoln.util.ClassTree;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public final class JsonArray implements JsonElement {

  private final List<JsonElement> array;

  public Object toObject(ClassTree classTree) {
    return ConvertersCache.getArrayConverter(classTree.rawType())
      .jsonElementsToObject(array, classTree);
  }

  public StringBuffer appendToSB(StringBuffer sb) {
    sb.append(Const.OPENING_BRACKET_STR);

    var iterator = array.iterator();
    iterator.forEachRemaining(el -> {
      el.appendToSB(sb);
      if (iterator.hasNext()) {
        sb.append(Const.ARRAY_MEMBERS_DELIMITER_STR);
      }
    });

    sb.append(Const.CLOSING_BRACKET_STR);
    return sb;
  }
}
