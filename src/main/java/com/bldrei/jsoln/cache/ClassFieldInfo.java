package com.bldrei.jsoln.cache;

import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

public record ClassFieldInfo(
  String name,
  boolean isNullable,
  ClassTreeWithConverters classTree,
  Optional<Method> getter,
  Optional<Method> setter,
  Class<?> dtoClass
) {
  public ClassFieldInfo {
    Objects.requireNonNull(name);
    Objects.requireNonNull(classTree);
    Objects.requireNonNull(getter);
    Objects.requireNonNull(setter);
    Objects.requireNonNull(dtoClass);

    if (Optional.class.equals(classTree.getRawType())) {
      throw BadDtoException.nestedOptional(dtoClass);
    }
  }

  public static ClassFieldInfo from(Class<?> dtoClass, Field field) {
    String name = field.getName();
    ClassTreeWithConverters classTree = ClassTreeWithConverters.fromField(field);
    boolean isOptional = Optional.class.equals(classTree.getRawType());
    if (isOptional) classTree = classTree.getGenericParameters()[0];
    return new ClassFieldInfo(
      name,
      isOptional,
      classTree,
      ReflectionUtil.findGetter(dtoClass, name, field.getType()),
      ReflectionUtil.findSetter(dtoClass, name, field.getType()),
      dtoClass
    );
  }
}
