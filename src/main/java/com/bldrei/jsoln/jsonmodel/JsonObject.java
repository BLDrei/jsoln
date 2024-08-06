package com.bldrei.jsoln.jsonmodel;

import com.bldrei.jsoln.Const;
import com.bldrei.jsoln.converter.object.ObjectConverter;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@AllArgsConstructor
public final class JsonObject implements JsonElement {
  private final @NotNull Map<String, JsonElement> kvMap;

  public Object toObject(@NotNull ClassTreeWithConverters classTree) {
    return ((ObjectConverter<?>) classTree.getConverter())
      .jsonElementsMapToObject(kvMap, classTree);
  }

  public StringBuffer appendToSB(StringBuffer sb) {
    sb.append(Const.OPENING_CURLY_BRACE_STR);

    var iterator = kvMap.entrySet().iterator();
    iterator.forEachRemaining(e -> {
      sb
        .append(Const.DOUBLE_QUOTE_STR)
        .append(e.getKey())
        .append(Const.DOUBLE_QUOTE_STR)
        .append(Const.KV_DELIMITER_STR);
      e.getValue().appendToSB(sb);
      if (iterator.hasNext()) {
        sb.append(Const.PARAMS_DELIMITER_STR);
      }
    });

    sb.append(Const.CLOSING_CURLY_BRACE_STR);

    return sb;
  }
}
