package com.bldrei.jsoln.converter;

import lombok.Getter;

@Getter
public abstract class PlainTypeConverter<T> {

  private final Class<T> type;

  protected PlainTypeConverter(Class<T> type) {
    this.type = type;
  }

//  protected boolean isTypeMatchingTheConverter(@NonNull Class<?> clazz) {
//    return type.equals(clazz); //consider switching back to map for performance, or even append matching converter to RecordFieldInfo
//  }
}
