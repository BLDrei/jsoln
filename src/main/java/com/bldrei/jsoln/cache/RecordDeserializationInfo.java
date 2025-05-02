package com.bldrei.jsoln.cache;

import com.bldrei.jsoln.Configuration;
import com.bldrei.jsoln.util.ClassTreeWithConverters;
import com.bldrei.jsoln.util.ReflectionUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class RecordDeserializationInfo<T> {
  @NotNull
  private final Constructor<T> canonicalConstructor;
  @NotNull
  private final ClassTreeWithConverters classTree;
  @NotNull
  private final List<RecordFieldInfo> fieldsInfo;

  public static <R> RecordDeserializationInfo<R> from(Class<R> recordClass, Configuration conf) {
    if (!recordClass.isRecord()) throw new IllegalStateException();

    return new RecordDeserializationInfo<>(
      ReflectionUtil.findCanonicalConstructor(recordClass),
      ClassTreeWithConverters.fromClass(recordClass),
      Arrays.stream(recordClass.getRecordComponents())
        .map(it -> RecordFieldInfo.from(it, conf))
        .toList()
    );
  }
}
