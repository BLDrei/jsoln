package com.bldrei.jsoln.util;

import com.bldrei.jsoln.converter.array.ArrayConverter;
import com.bldrei.jsoln.converter.bool.BooleanConverter;
import com.bldrei.jsoln.converter.number.NumberConverter;
import com.bldrei.jsoln.converter.object.ObjectConverter;
import com.bldrei.jsoln.converter.text.TextConverter;
import com.bldrei.jsoln.jsonmodel.JsonElement;

public class SerializeUtil {
  private SerializeUtil() {}

  public static JsonElement convertObjectToJsonElement(Object obj, ClassTreeWithConverters classTree) {
    return switch (classTree.getConverter()) {
      case TextConverter<?> tc -> tc.textTypeToJsonText(obj);
      case NumberConverter<?> nc -> nc.numberTypeToJsonNumber(obj);
      case BooleanConverter bc -> bc.booleanTypeToJsonBoolean(obj);
      case ArrayConverter<?> ac -> ac.collectionToJsonArray(obj, classTree);
      case ObjectConverter<?> oc -> oc.objectTypeToJsonObject(obj, classTree);
      default -> throw new IllegalStateException();
    };
  }
}
