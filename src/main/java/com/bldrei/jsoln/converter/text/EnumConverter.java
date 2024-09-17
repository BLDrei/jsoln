package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.util.ReflectionUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public final class EnumConverter<E> extends TextConverter<E> {

  private final @NotNull Method valueOf;

  public EnumConverter(@NotNull Class<E> enumType) {
    //todo: @Jsoln.EnumValue
    this.valueOf = ReflectionUtil.findEnumValueOfMethod(enumType);
  }

  @Override
  @SuppressWarnings("unchecked")
  public E javaify(@NotNull String value) {
    return (E) ReflectionUtil.invokeStaticMethod(this.valueOf, value);
  }

  @Override
  protected String stringify(@NotNull E flatValue) {
    return flatValue.toString();
  }
}
