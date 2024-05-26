package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.util.ReflectionUtil;

import java.lang.reflect.Method;

public final class EnumConverter<E> extends TextConverter<E> {

  private final Method valueOf;

  public EnumConverter(Class<E> enumType) {
    super(enumType);
    this.valueOf = ReflectionUtil.findPublicMethod(enumType, "valueOf", String.class)
      .orElseThrow(() -> new BadDtoException("public valueOf(String) method not found for enum: " + enumType));
  }

  @Override
  @SuppressWarnings("unchecked")
  public E convert(String value) {
    return (E) ReflectionUtil.invokeStaticMethod(this.valueOf, value); //what if value=null?
  }

//  @Override
//  public static boolean isTypeMatchingTheConverter(@NonNull Class<?> type) {
//    return type.isEnum();
//  }
}
