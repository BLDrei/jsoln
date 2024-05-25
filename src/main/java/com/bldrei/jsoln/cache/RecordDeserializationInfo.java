package com.bldrei.jsoln.cache;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import static com.bldrei.jsoln.util.ReflectionUtil.getCanonicalConstructor;

public record RecordDeserializationInfo(
  Constructor<?> canonicalConstructor,
  List<RecordFieldInfo> fieldsInfo
) {
  public static RecordDeserializationInfo from(Class<?> recordClass) {
    if (!recordClass.isRecord()) throw new IllegalStateException();

    return new RecordDeserializationInfo(
      getCanonicalConstructor(recordClass),
      Arrays.stream(recordClass.getRecordComponents())
        .map(RecordFieldInfo::from)
        .toList()
    );
  }
}
