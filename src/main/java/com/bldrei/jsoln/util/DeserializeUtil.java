package com.bldrei.jsoln.util;

import com.bldrei.jsoln.configuration.Configuration;
import com.bldrei.jsoln.converter.array.ArrayConverter;
import com.bldrei.jsoln.converter.bool.BooleanConverter;
import com.bldrei.jsoln.converter.number.NumberConverter;
import com.bldrei.jsoln.converter.object.ObjectConverter;
import com.bldrei.jsoln.converter.text.TextConverter;
import com.bldrei.jsoln.exception.JsolnException;
import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;

public class DeserializeUtil {

  private DeserializeUtil() {}

  public static Object javaifyJsonModel(@NotNull JsonNode jsonNode, @NotNull ClassTreeWithConverters classTree, @NotNull Configuration conf) {
    if (jsonNode.isNull()) return null;

    return switch (classTree.getConverter()) {
      case TextConverter<?> tc
        when jsonNode.isTextual() -> tc.javaify(jsonNode.textValue(), conf);
      case NumberConverter<?> nc
        when jsonNode.isNumber() -> nc.javaify(jsonNode.numberValue().toString(), conf);
      case BooleanConverter bc
        when jsonNode.isBoolean() -> jsonNode.asBoolean();
      case ArrayConverter<?> ac
        when jsonNode.isArray() -> ac.javaify(jsonNode, classTree, conf);
      case ObjectConverter<?> oc
        when jsonNode.isObject() -> oc.javaify(jsonNode, classTree, conf);
      default -> throw JsolnException.cannotCovertJsonElementToType(classTree, jsonNode.getClass());
    };
  }
}
