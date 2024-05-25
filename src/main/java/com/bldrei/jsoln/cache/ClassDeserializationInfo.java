package com.bldrei.jsoln.cache;

import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.util.ReflectionUtil;
import lombok.Getter;
import lombok.NonNull;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
public final class ClassDeserializationInfo<C> {
  @NonNull
  private final Constructor<C> noArgsConstructor;
  @NonNull
  private final List<ClassFieldInfo> fieldsInfo;

  private ClassDeserializationInfo(Constructor<C> noArgsConstructor, List<ClassFieldInfo> fieldsInfo) {
    if (fieldsInfo.isEmpty()) {
      throw new BadDtoException("Dto has no fields");
    }
    if (fieldsInfo.stream().map(ClassFieldInfo::setter).allMatch(Optional::isEmpty)) {
      throw new BadDtoException("None of the fields has a setter. Consider using a record.");
    }
    this.noArgsConstructor = noArgsConstructor;
    this.fieldsInfo = fieldsInfo;
  }

  public static <C> ClassDeserializationInfo<C> from(Class<C> clazz) {
    if (clazz.isRecord()) throw new IllegalStateException();

    return new ClassDeserializationInfo<>(
      ReflectionUtil.findNoArgsConstructor(clazz)
        .orElseThrow(() -> new BadDtoException("NoArgsConstructor must be present: " + clazz)),
      Arrays.stream(ReflectionUtil.getPrivateNonStaticFields(clazz))
        .map(fld -> ClassFieldInfo.from(clazz, fld))
        .toList()
    );
  }
}
