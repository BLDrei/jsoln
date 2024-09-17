package com.bldrei.jsoln.util;

import com.bldrei.jsoln.converter.array.ArrayConverter;
import com.bldrei.jsoln.converter.bool.BooleanConverter;
import com.bldrei.jsoln.converter.number.NumberConverter;
import com.bldrei.jsoln.converter.object.ObjectConverter;
import com.bldrei.jsoln.converter.text.TextConverter;

public class SerializeUtil {
  private SerializeUtil() {}

  public static Object javaObjectToJsonModel(Object obj, ClassTreeWithConverters classTree) {
    return switch (classTree.getConverter()) {
      case TextConverter<?> tc -> tc.toJsonModel(obj);
      case NumberConverter<?> nc -> nc.toJsonModel(obj);
      case BooleanConverter bc -> bc.toJsonModel(obj);
      case ArrayConverter<?> ac -> ac.toJsonModel(obj, classTree);
      case ObjectConverter<?> oc -> oc.toJsonModel(obj, classTree);
      default -> throw new IllegalStateException();
    };
  }
}
