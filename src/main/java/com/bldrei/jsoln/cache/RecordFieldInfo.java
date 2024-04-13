package com.bldrei.jsoln.cache;

import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.util.ClassTree;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Objects;
import java.util.Optional;

public record RecordFieldInfo(
  String name,
  boolean isNullable,
  ClassTree classTree,
  Method accessor,
  Class<?> dtoClass
) {
  public RecordFieldInfo {
    Objects.requireNonNull(name);
    Objects.requireNonNull(classTree);
    Objects.requireNonNull(accessor);
    Objects.requireNonNull(dtoClass);

    if (Optional.class.equals(classTree.rawType())) {
      throw BadDtoException.nestedOptional(dtoClass);
    }
  }

  public static RecordFieldInfo from(RecordComponent field) {
    ClassTree tree = ClassTree.fromType(field.getGenericType());
    boolean isNullable = Optional.class.equals(tree.rawType());
    if (isNullable) tree = ClassTree.fromType(tree.genericParameters()[0]);
    return new RecordFieldInfo(
      field.getName(),
      isNullable,
      tree,
      field.getAccessor(),
      field.getDeclaringRecord()
    );
  }
}