package com.bldrei.jsoln.cache;

import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.util.ReflectionUtil;
import lombok.Builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Builder
public record ClassDeserializationInfo(
  Optional<Constructor<?>> allArgsConstructor,
  Optional<Constructor<?>> noArgsConstructor,
  List<ClassFieldInfo> fieldsInfo
) {

  public ClassDeserializationInfo {
    Objects.requireNonNull(allArgsConstructor);
    Objects.requireNonNull(noArgsConstructor);
    Objects.requireNonNull(fieldsInfo);

    if (allArgsConstructor.isEmpty() && noArgsConstructor.isEmpty()) {
      throw new BadDtoException("AllArgsConstructor or NoArgsConstructor must be present");
    }
    if (fieldsInfo.isEmpty()) {
      throw new BadDtoException("Dto has no fields");
    }
    if (fieldsInfo.stream().map(ClassFieldInfo::setter).allMatch(Optional::isEmpty)) {
      throw new BadDtoException("None of the fields has a setter");
    }
  }

  public static ClassDeserializationInfo from(Class<?> clazz) {
    if (clazz.isRecord()) throw new IllegalStateException();

    Field[] notStaticFields = ReflectionUtil.getNonStaticFields(clazz);
    return ClassDeserializationInfo.builder()
      .allArgsConstructor(ReflectionUtil.getAllArgsConstructor(clazz, notStaticFields))
      .noArgsConstructor(ReflectionUtil.getNoArgsConstructor(clazz))
      .fieldsInfo(Arrays.stream(notStaticFields).map(fld -> ClassFieldInfo.from(clazz, fld)).toList())
      .build();
  }
}
