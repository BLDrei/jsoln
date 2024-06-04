package com.bldrei.jsoln.converter;

import lombok.Getter;
import lombok.NonNull;

import java.util.stream.Stream;

@Getter
public abstract class ArrayConverter<C> {

  private final Class<C> type;

  protected ArrayConverter(Class<C> type) {
    this.type = type;
  }

  public abstract C convert(Stream<?> stream);

  protected abstract String stringifyT(@NonNull C flatValue);

  @SuppressWarnings("unchecked")
  public String stringify(@NonNull Object flatValue) { //i don't like Object, consider how to bring back T
    return stringifyT((C) flatValue);
  }
}
