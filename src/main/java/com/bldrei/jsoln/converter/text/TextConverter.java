package com.bldrei.jsoln.converter.text;

import com.bldrei.jsoln.converter.PlainTypeConverter;
import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.jsonmodel.JsonText;
import lombok.NonNull;

public abstract sealed class TextConverter<T> extends PlainTypeConverter<T> permits LocalDateConverter, LocalDateTimeConverter, StringConverter, EnumConverter {
  protected TextConverter(Class<T> type) {
    super(type);
  }

  public abstract T stringToObject(String value);

  @Override
  public JsonElement objectToJsonElement(@NonNull Object flatValue) {
    return new JsonText(stringify(flatValue));
  }
}
