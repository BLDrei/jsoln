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
      case TextConverter<?> tc -> tc.objectToJsonElement(obj);
      case NumberConverter<?> nc -> nc.objectToJsonElement(obj);
      case BooleanConverter bc -> bc.objectToJsonElement(obj);
      case ArrayConverter<?> ac -> ac.objectToJsonArray(obj, classTree);
      case ObjectConverter<?> oc -> oc.objectToJsonObject(obj, classTree);
      default -> throw new IllegalStateException();
    };
  }
}
