package com.bldrei.jsoln.cache;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.jsonmodel.JsonModelType;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.TypeUtil;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.util.Objects;

public record RecordFieldInfo(
  String name,
  boolean isRequired,
  ClassTreeWithConverters classTree,
  Method accessor,
  JsonModelType jsonModelType,
  Class<?> dtoClass
) {
  public RecordFieldInfo {
    Objects.requireNonNull(name);
    Objects.requireNonNull(classTree);
    Objects.requireNonNull(accessor);
    Objects.requireNonNull(jsonModelType);
    Objects.requireNonNull(dtoClass);
  }

  public static RecordFieldInfo from(RecordComponent field, Configuration conf) {
    Type originalType = field.getGenericType();
    boolean isWrappedWithOptional = TypeUtil.isOptional(originalType);

    boolean isRequired = switch (conf.getRequiredFieldsDefinitionMode()) {
      case ALLOW_OPTIONAL_FOR_FIELDS -> !isWrappedWithOptional;
      case ALL_FIELDS_NULLABLE -> {
        if (isWrappedWithOptional) {
          throw new BadDtoException("Field '%s' in '%s' is of Optional type, but required fields definition mode is %s, so Optional is not supported");
        }
        yield false;
      }
    };

    ClassTreeWithConverters tree = ClassTreeWithConverters.fromType(
      isWrappedWithOptional ? TypeUtil.unwrapOptional(originalType) : originalType
    );
    return new RecordFieldInfo(
      field.getName(),
      isRequired,
      tree,
      field.getAccessor(),
      tree.getJsonDataType(),
      field.getDeclaringRecord()
    );
  }


}
