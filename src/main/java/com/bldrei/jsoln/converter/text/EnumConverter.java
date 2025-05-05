package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.configuration.Configuration;
import com.bldrei.jsoln.util.ReflectionUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public final class EnumConverter<E> extends TextConverter<E> {

  private final @NotNull Method valueOf;

  public EnumConverter(@NotNull Class<E> enumType) {
    this.valueOf = ReflectionUtil.findEnumValueOfMethod(enumType);
  }

  @Override
  @SuppressWarnings("unchecked")
  public E javaify(@NotNull String value, @NotNull Configuration conf) {
    return (E) ReflectionUtil.invokeStaticMethod(this.valueOf, value);
  }

  @Override
  protected String stringify(@NotNull E flatValue, @NotNull Configuration conf) {
    return flatValue.toString();
  }
}
