package com.bldrei.jsoln.converter;

import com.bldrei.jsoln.jsonmodel.JsonBoolean;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import lombok.NonNull;

public class BooleanConverter extends PlainTypeConverter<Boolean> {

  public BooleanConverter() {
    super(Boolean.class);
  }

  @Override
  protected String stringifyT(@NonNull Boolean flatValue) {
    return flatValue.toString();
  }

  @Override
  public JsonElement objectToJsonElement(@NonNull Object flatValue) {
    return switch (flatValue) {
      case Boolean b -> b ? JsonBoolean.TRUE : JsonBoolean.FALSE;
      default -> throw new IllegalStateException();
    };
  }
}
