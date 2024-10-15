package com.bldrei.jsoln.util;

import com.bldrei.jsoln.converter.array.ArrayConverter;
import com.bldrei.jsoln.converter.bool.BooleanConverter;
import com.bldrei.jsoln.converter.number.NumberConverter;
import com.bldrei.jsoln.converter.object.ObjectConverter;
import com.bldrei.jsoln.converter.text.TextConverter;

public class SerializeUtil {
  private SerializeUtil() {}

  public static String stringify(Object obj, ClassTreeWithConverters classTree, StringBuilder sb) {
    return switch (classTree.getConverter()) {
      case TextConverter<?> tc -> tc.stringify(obj, sb);
      case NumberConverter<?> nc -> nc.stringify(obj, sb);
      case BooleanConverter bc -> bc.stringify(obj, sb);
      case ArrayConverter<?> ac -> ac.stringify(obj, classTree, sb);
      case ObjectConverter<?> oc -> oc.stringify(obj, classTree, sb);
      default -> throw new IllegalStateException();
    };
  }
}
