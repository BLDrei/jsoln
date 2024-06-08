package com.bldrei.jsoln.converter;

import com.bldrei.jsoln.jsonmodel.JsonElement;
import lombok.NonNull;

public abstract class PlainTypeConverter<T> implements AbstractConverter {

  protected abstract String stringify(@NonNull T flatValue);

  protected abstract JsonElement objectToJsonElement(@NonNull Object flatValue);
}
