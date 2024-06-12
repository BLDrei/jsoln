package com.bldrei.jsoln.cache;

import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.TypeUtil;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

public record RecordFieldInfo(
  String name,
  boolean isNullable,
  ClassTreeWithConverters classTree,
  Method accessor,
  JsonElement.Type jsonType,
  Class<?> dtoClass
) {
  public RecordFieldInfo {
    Objects.requireNonNull(name);
    Objects.requireNonNull(classTree);
    Objects.requireNonNull(accessor);
    Objects.requireNonNull(jsonType);
    Objects.requireNonNull(dtoClass);

    if (Optional.class.equals(classTree.getRawType())) {
      throw BadDtoException.nestedOptional(dtoClass);
    }
  }

  public static RecordFieldInfo from(RecordComponent field) {
    Type originalType = field.getGenericType();
    boolean isNullable = TypeUtil.isOptional(originalType);

    ClassTreeWithConverters tree = ClassTreeWithConverters.fromType(
      isNullable ? TypeUtil.unwrapOptional(originalType) : originalType
    );
    return new RecordFieldInfo(
      field.getName(),
      isNullable,
      tree,
      field.getAccessor(),
      tree.getJsonDataType(),
      field.getDeclaringRecord()
    );
  }
}
