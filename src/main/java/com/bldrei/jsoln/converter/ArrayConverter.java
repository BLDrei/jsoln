package com.bldrei.jsoln.converter;

import com.bldrei.jsoln.jsonmodel.JsonElement;
import com.bldrei.jsoln.util.ClassTree;
import com.bldrei.jsoln.util.SerializeUtil;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Stream;

@Getter
public abstract class ArrayConverter<C> {

  private final Class<C> type;

  protected ArrayConverter(Class<C> type) {
    this.type = type;
  }

  public abstract C convert(Stream<?> stream);

  protected abstract Stream<?> toStream(@NonNull C flatValue);

  @SuppressWarnings("unchecked")
  public List<JsonElement> toJsonElementsList(@NonNull Object flatValue, ClassTree classTree) { //i don't like Object, consider how to bring back T
    ClassTree collectionOfWhat = classTree.genericParameters()[0];
    return toStream((C) flatValue)
      .map(it -> SerializeUtil.convertObjectToJsonElement(it, collectionOfWhat))
      .toList();
  }
}