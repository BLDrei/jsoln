package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.exception.BadDtoException;
import com.bldrei.jsoln.util.ReflectionUtil;
import lombok.NonNull;

import java.lang.reflect.Method;

public final class EnumConverter<E> extends TextConverter<E> {

  private final Method valueOf;

  public EnumConverter(@NonNull Class<E> enumType) {
    this.valueOf = ReflectionUtil.findPublicMethod(enumType, "valueOf", String.class)
      .orElseThrow(() -> new BadDtoException("public valueOf(String) method not found for enum: " + enumType));
  }

  @Override
  @SuppressWarnings("unchecked")
  public E stringToObject(@NonNull String value) {
    return (E) ReflectionUtil.invokeStaticMethod(this.valueOf, value); //what if value=null? actually, it is possible if exception throwing on missing required value is disabled
  }

  @Override
  protected String stringify(@NonNull E flatValue) {
    return flatValue.toString();
  }
}
