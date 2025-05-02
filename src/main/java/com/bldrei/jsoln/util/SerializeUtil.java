package com.bldrei.jsoln.util;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.converter.array.ArrayConverter;
import com.bldrei.jsoln.converter.bool.BooleanConverter;
import com.bldrei.jsoln.converter.number.NumberConverter;
import com.bldrei.jsoln.converter.object.ObjectConverter;
import com.bldrei.jsoln.converter.text.TextConverter;

public class SerializeUtil {
  private SerializeUtil() {}

  public static String stringify(Object obj, ClassTreeWithConverters classTree, StringBuilder sb, Configuration conf) {
    return switch (classTree.getConverter()) {
      case TextConverter<?> tc -> tc.stringify(obj, sb, conf);
      case NumberConverter<?> nc -> nc.stringify(obj, sb, conf);
      case BooleanConverter bc -> bc.stringify(obj, sb, conf);
      case ArrayConverter<?> ac -> ac.stringify(obj, classTree, sb, conf);
      case ObjectConverter<?> oc -> oc.stringify(obj, classTree, sb, conf);
      default -> throw new IllegalStateException();
    };
  }
}
