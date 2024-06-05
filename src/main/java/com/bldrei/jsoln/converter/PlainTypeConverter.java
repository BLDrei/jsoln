package com.bldrei.jsoln.converter;

import com.bldrei.jsoln.jsonmodel.JsonElement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class PlainTypeConverter<T> {

  private final Class<T> type;

  protected PlainTypeConverter(Class<T> type) {
    this.type = type;
  }

  protected abstract String stringifyT(@NonNull T flatValue);

  @SuppressWarnings("unchecked")
  public String stringify(@NonNull Object flatValue) { //i don't like Object, consider how to bring back T
    return stringifyT((T) flatValue);
  }

  public abstract JsonElement objectToJsonElement(@NonNull Object flatValue);
}
