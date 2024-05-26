package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.cache.Cache;
import com.bldrei.jsoln.util.ReflectionUtil;
import lombok.NonNull;

import java.lang.reflect.Method;

public final class EnumConverter<E> extends TextConverter<E> {

  private final Method valueOf;

  public EnumConverter(Class<E> enumType) {
    super(enumType);
    this.valueOf = Cache.getEnumValueOf(enumType);
  }

  @Override
  public E convert(String value) {
    return (E) ReflectionUtil.invokeStaticMethod(this.valueOf, value); //what if value=null?
  }

//  @Override
//  public static boolean isTypeMatchingTheConverter(@NonNull Class<?> type) {
//    return type.isEnum();
//  }
}
