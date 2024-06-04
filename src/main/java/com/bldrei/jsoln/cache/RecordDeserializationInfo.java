package com.bldrei.jsoln.cache;

import com.bldrei.jsoln.util.ClassTree;
import com.bldrei.jsoln.util.ReflectionUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class RecordDeserializationInfo<T> {
  @NonNull
  private final Constructor<T> canonicalConstructor;
  @NonNull
  private final ClassTree classTree;
  @NonNull
  private final List<RecordFieldInfo> fieldsInfo;

  public static <R> RecordDeserializationInfo<R> from(Class<R> recordClass) {
    if (!recordClass.isRecord()) throw new IllegalStateException();

    return new RecordDeserializationInfo<>(
      ReflectionUtil.findCanonicalConstructor(recordClass),
      ClassTree.fromClass(recordClass),
      Arrays.stream(recordClass.getRecordComponents())
        .map(RecordFieldInfo::from)
        .toList()
    );
  }
}
