package com.bldrei.jsoln.converter.bool;

import com.bldrei.jsoln.converter.AbstractConverter;
import com.bldrei.jsoln.jsonmodel.JsonBoolean;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import lombok.NonNull;

public final class BooleanConverter implements AbstractConverter {

  public JsonElement objectToJsonElement(@NonNull Object flatValue) {
    return switch (flatValue) {
      case Boolean b -> b ? JsonBoolean.TRUE : JsonBoolean.FALSE;
      default -> throw new IllegalStateException();
    };
  }
}
